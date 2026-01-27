
package com.example.bidcompauction.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.data.model.LoginRequest
import com.example.bidcompauction.data.model.LoginResponse
import com.example.bidcompauction.network.RetrofitClient
import com.example.bidcompauction.utils.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    // 🎯 Mengubah Success untuk membawa LoginResponse lengkap
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

// 🎯 Inject AuthManager melalui constructor (disarankan) atau context
class LoginViewModel(private val authManager: AuthManager) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginState.Loading
                val response = RetrofitClient.api.login(LoginRequest(email, password)) // Asumsi RetrofitClient.apiService sudah diperbaiki

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    // 🎯 Simpan data ke SharedPreferences
                    authManager.saveLogin(loginResponse)

                    _loginState.value = LoginState.Success(loginResponse)
                } else {
                    _loginState.value = LoginState.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Network error")
            }
        }
    }

    // Fungsi pembantu untuk membuat ViewModel
    class Factory(private val authManager: AuthManager) : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(authManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}