package data.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import data.model.PaymentResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentValidationViewModel(application: Application) : AndroidViewModel(application) {
    private val authManager = AuthManager(application)

    private val _payments = MutableStateFlow<List<PaymentResponse>>(emptyList())
    val payments = _payments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private fun getAuthHeader() = "Bearer ${authManager.getToken()}"

    fun fetchAllPayments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.getAllPayments(getAuthHeader())
                if (response.isSuccessful) {
                    _payments.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun validatePayment(paymentId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.validatePayment(getAuthHeader(), paymentId)
                if (response.isSuccessful) {
                    fetchAllPayments() // Refresh data setelah validasi
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}