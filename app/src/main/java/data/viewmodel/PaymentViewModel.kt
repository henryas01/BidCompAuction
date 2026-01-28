package data.viewmodel


import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import com.example.bidcompauction.utils.FileUtil
import data.model.PaymentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class PaymentViewModel(application: Application) : AndroidViewModel(application) {
    private val authManager = AuthManager(application)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _paymentResult = MutableStateFlow<PaymentResponse?>(null)
    val paymentResult = _paymentResult.asStateFlow()

    fun uploadPayment(
        context: Context,
        sourceType: String,
        sourceId: Int,
        imageUri: Uri,
        onSuccess: () -> Unit
    ) {
        Log.d("PAYMENT_DEBUG", "Starting upload: Type=$sourceType, ID=$sourceId")

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 1. Konversi Uri ke File
                val file = FileUtil.getFileFromUri(context, imageUri)
                if (!file.exists()) {
                    Log.e("PAYMENT_DEBUG", "File does not exist at path: ${file.absolutePath}")
                    return@launch
                }

                // 2. Siapkan Part Gambar
                // Nama "images" harus sama persis dengan yang diminta backend (Field -F 'images=@...')
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val bodyImage = MultipartBody.Part.createFormData("images", file.name, requestFile)

                // 3. Siapkan Part Teks (Gunakan multipart/form-data agar tidak ada tanda kutip ganda/extra quotes)
                val typePart = sourceType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
                val idPart = sourceId.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())

                val token = "Bearer ${authManager.getToken()}"

                Log.d("PAYMENT_DEBUG", "Sending Request to API...")
                val response = RetrofitClient.api.processPayment(token, typePart, idPart, bodyImage)

                if (response.isSuccessful) {
                    Log.d("PAYMENT_DEBUG", "Upload Success: ${response.body()}")
                    _paymentResult.value = response.body()
                    onSuccess()
                } else {
                    val errorMsg = response.errorBody()?.string()
                    Log.e("PAYMENT_DEBUG", "Upload Failed: Code=${response.code()}, Msg=$errorMsg")
                }
            } catch (e: Exception) {
                Log.e("PAYMENT_DEBUG", "Exception during upload: ${e.message}")
                e.printStackTrace()
            } finally {
                _isLoading.value = false
                Log.d("PAYMENT_DEBUG", "Upload Process Finished")
            }
        }
    }
}