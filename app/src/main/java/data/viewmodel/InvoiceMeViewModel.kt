package data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import data.model.UserPaymentInvoiceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InvoiceMeViewModel(application: Application) : AndroidViewModel(application) {
    private val authManager = AuthManager(application)

    private val _invoices = MutableStateFlow<List<UserPaymentInvoiceResponse>>(emptyList())
    val invoices = _invoices.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchMyInvoices() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val token = "Bearer ${authManager.getToken()}"
                val response = RetrofitClient.api.getMyInvoices(token)
                if (response.isSuccessful) {
                    _invoices.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}