package data.viewmodel

import android.app.Application
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

    fun addProduct(request: AdminProductRequest) { // Pastikan menerima objek request
        viewModelScope.launch {
            try {
                // 1. Ambil URI dari request, jika null batalkan (atau beri peringatan)
                val imageUri = request.imageUri ?: return@launch

                // 2. Konversi Uri ke File menggunakan FileUtil
                val file = FileUtil.getFileFromUri(getApplication(), imageUri)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val bodyImage = MultipartBody.Part.createFormData("images", file.name, requestFile)

                // 3. Konversi String ke RequestBody (Format Multipart)
                val namePart = request.name.toRequestBody("text/plain".toMediaTypeOrNull())
                val pricePart = request.price.toRequestBody("text/plain".toMediaTypeOrNull())
                val stockPart = request.stock.toRequestBody("text/plain".toMediaTypeOrNull())
                val descPart = request.desc.toRequestBody("text/plain".toMediaTypeOrNull())

                // 4. Kirim ke API
                RetrofitClient.api.addProduct(
                    token = getAuthHeader(),
                    name = namePart,
                    price = pricePart,
                    stock = stockPart,
                    desc = descPart,
                    image = bodyImage
                )

                // 5. Refresh data setelah sukses
                fetchProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProduct(id: Int, name: String, price: Long, stock: Int, desc: String) {
        viewModelScope.launch {
            try {
                val body = mapOf(
                    "name" to name,
                    "price" to price,
                    "stock" to stock,
                    "desc" to desc
                )
                RetrofitClient.api.updateProduct(getAuthHeader(), id, body)
                fetchProducts()
            } catch (e: Exception) { e.printStackTrace() }
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