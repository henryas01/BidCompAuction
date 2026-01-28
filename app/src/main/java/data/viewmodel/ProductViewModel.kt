package data.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import com.example.bidcompauction.utils.FileUtil
import data.model.AdminProductRequest
import data.model.AdminProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val authManager = AuthManager(application)

    private val _products = MutableStateFlow<List<AdminProductResponse>>(emptyList())
    val products: StateFlow<List<AdminProductResponse>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init { fetchProducts() }

    private fun getAuthHeader(): String = "Bearer ${authManager.getToken()}"

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.getProducts(getAuthHeader())
                _products.value = response
            } catch (e: Exception) { e.printStackTrace() }
            _isLoading.value = false
        }
    }

    // --- ADD PRODUCT ---
    fun addProduct(context: Context, name: String, price: Long, stock: Int, desc: String, imageUri: Uri?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val pricePart = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockPart = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val descPart = desc.toRequestBody("text/plain".toMediaTypeOrNull())

                val imagePart = imageUri?.let { uri ->
                    val file = FileUtil.getFileFromUri(context, uri)
                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                }

                if (imagePart != null) {
                    RetrofitClient.api.addProduct(getAuthHeader(), namePart, pricePart, stockPart, descPart, imagePart)
                    fetchProducts()
                }
            } catch (e: Exception) { e.printStackTrace() }
            _isLoading.value = false
        }
    }

    // --- UPDATE PRODUCT (PATCH) ---
    fun updateProduct(context: Context, id: Int, name: String, price: Long, stock: Int, desc: String, imageUri: Uri?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Konversi data ke RequestBody
                val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val pricePart = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockPart = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val descPart = desc.toRequestBody("text/plain".toMediaTypeOrNull())

                // Part gambar opsional
                val imagePart = imageUri?.let { uri ->
                    val file = FileUtil.getFileFromUri(context, uri)
                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                }

                // Perbaikan: Gunakan getAuthHeader() bukan variabel token yang tidak ada
                val response = RetrofitClient.api.updateProduct(
                    token = getAuthHeader(),
                    id = id,
                    name = namePart,
                    price = pricePart,
                    stock = stockPart,
                    desc = descPart,
                    image = imagePart
                )

                if (response.isSuccessful) {
                    fetchProducts() // Refresh list setelah update
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.deleteProduct(getAuthHeader(), id)
                fetchProducts()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}