package data.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import com.example.bidcompauction.utils.FileUtil
import data.model.AdminFlashSaleRequest
import data.model.AdminFlashSaleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class FlashSaleViewModel(application: Application) : AndroidViewModel(application) {
    private val authManager = AuthManager(application)

    private val _items = MutableStateFlow<List<AdminFlashSaleResponse>>(emptyList())
    val items: StateFlow<List<AdminFlashSaleResponse>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init { fetchFlashSales() }

    private fun getAuthHeader(): String = "Bearer ${authManager.getToken()}"

    private val _activeBids = MutableStateFlow<List<AdminFlashSaleResponse>>(emptyList())
    val activeBids: StateFlow<List<AdminFlashSaleResponse>> = _activeBids.asStateFlow()

    fun addBid(item: AdminFlashSaleResponse) {
        _activeBids.value = _activeBids.value + item
//        println("addBid $_activeBids")

        println( "addBid $item")
    }

    fun fetchFlashSales() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.getFlashSales(getAuthHeader())
                _items.value = response
            } catch (e: Exception) { e.printStackTrace() }
            _isLoading.value = false
        }
    }

    fun addFlashSale(request: AdminFlashSaleRequest) {
        viewModelScope.launch {
            try {
                // 1. Ambil URI dari request
                val imageUri = request.imageUri ?: return@launch

                // 2. Konversi Uri ke File menggunakan FileUtil (Multipart Image)
                val file = FileUtil.getFileFromUri(getApplication(), imageUri)
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                // Sesuaikan key "image" atau "images" dengan ApiService kamu
                val bodyImage = MultipartBody.Part.createFormData("images", file.name, requestFile)

                // 3. Konversi String/Data ke RequestBody (Format Multipart)
                val namePart = request.name.toRequestBody("text/plain".toMediaTypeOrNull())
                val pricePart = request.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockPart = request.stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val descPart = request.desc.toRequestBody("text/plain".toMediaTypeOrNull())
                val startAtPart = request.startAt.toRequestBody("text/plain".toMediaTypeOrNull())
                val endAtPart = request.endAt.toRequestBody("text/plain".toMediaTypeOrNull())

                // 4. Kirim ke API
                RetrofitClient.api.addFlashSale(
                    token = getAuthHeader(),
                    name = namePart,
                    price = pricePart,
                    stock = stockPart,
                    desc = descPart,
                    startAt = startAtPart,
                    endAt = endAtPart,
                    image = bodyImage
                )

                // 5. Refresh data
                fetchFlashSales()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateFlashSale(
        context: android.content.Context,
        id: Int,
        name: String,
        price: Long,
        stock: Int,
        desc: String,
        startAt: String,
        endAt: String,
        imageUri: Uri? // Terima URI gambar baru
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Konversi String ke RequestBody
                val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val pricePart = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val stockPart = stock.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val descPart = desc.toRequestBody("text/plain".toMediaTypeOrNull())
                val startAtPart = startAt.toRequestBody("text/plain".toMediaTypeOrNull())
                val endAtPart = endAt.toRequestBody("text/plain".toMediaTypeOrNull())
                val isActivePart = "true".toRequestBody("text/plain".toMediaTypeOrNull())

                // Konversi Uri ke MultipartBody.Part (Hanya jika ada gambar baru)
                val imagePart = imageUri?.let { uri ->
                    val file = FileUtil.getFileFromUri(context, uri)
                    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestFile)
                }

                // Panggil API dengan format Multipart
                val response = RetrofitClient.api.updateFlashSaleMultipart(
                    token = getAuthHeader(),
                    id = id,
                    name = namePart,
                    price = pricePart,
                    stock = stockPart,
                    descriptions = descPart,
                    startAt = startAtPart,
                    endAt = endAtPart,
                    isActive = isActivePart,
                    image = imagePart // Bisa null jika tidak ganti gambar
                )

                if (response.isSuccessful) {
                    fetchFlashSales()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteFlashSale(id: Int) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.deleteFlashSale(getAuthHeader(), id)
                fetchFlashSales()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}