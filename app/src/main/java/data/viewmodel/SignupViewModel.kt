

package com.example.bidcompauction.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bidcompauction.data.model.SignupRequest
import com.example.bidcompauction.data.model.SignupResponse
import com.example.bidcompauction.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val response: SignupResponse) : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> = _uiState

    fun signup(request: SignupRequest) {
        _uiState.value = SignupUiState.Loading

        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.signupUser(request)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = SignupUiState.Success(response.body()!!)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                    _uiState.value = SignupUiState.Error("Signup failed: $errorMessage")
                }
            } catch (e: Exception) {
                _uiState.value = SignupUiState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        _uiState.value = SignupUiState.Idle
    }
}