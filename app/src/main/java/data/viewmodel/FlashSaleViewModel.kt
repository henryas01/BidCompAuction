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

    fun updateFlashSale(id: Int, name: String, price: Long, stock: Int, desc: String, startAt: String, endAt: String) {
        viewModelScope.launch {
            try {
                val body = mapOf(
                    "name" to name,
                    "price" to price,
                    "stock" to stock,
                    "desc" to desc,
                    "startAt" to startAt,
                    "endAt" to endAt
                )
                RetrofitClient.api.updateFlashSale(getAuthHeader(), id, body)
                fetchFlashSales()
            } catch (e: Exception) { e.printStackTrace() }
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