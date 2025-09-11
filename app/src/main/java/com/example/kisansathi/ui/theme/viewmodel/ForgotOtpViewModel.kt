package com.example.kisansathi.ui.theme.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.kisansathi.network.RetrofitInstance // Or inject AuthRepository
import com.example.kisansathi.ui.theme.models.VerifyForgotOtpRequest
import com.example.kisansathi.ui.theme.models.VerifyForgotOtpResponse
import com.example.kisansathi.ui.theme.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Assuming ApiResult sealed class is already defined

class ForgotOtpViewModel : ViewModel() { // For production, inject AuthRepository

    private val authRepository = AuthRepository(RetrofitInstance.api) // Or inject

    private val _verifyOtpResult = MutableStateFlow<ApiResult<VerifyForgotOtpResponse>?>(null)
    val verifyOtpResult: StateFlow<ApiResult<VerifyForgotOtpResponse>?> = _verifyOtpResult

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _verifyOtpResult.value = ApiResult.Loading
            try {
                val request = VerifyForgotOtpRequest(email = email, otp = otp)
                val response = authRepository.verifyForgotPasswordOtp(request)

                if (response.isSuccessful && response.body() != null) {
                    _verifyOtpResult.value = ApiResult.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (!errorBody.isNullOrBlank()) {
                        errorBody
                    } else {
                        response.message() ?: "Failed to verify OTP"
                    }
                    _verifyOtpResult.value = ApiResult.Error(errorMessage, response.code())
                }
            } catch (e: Exception) {
                _verifyOtpResult.value = ApiResult.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun clearVerifyOtpResult() {
        _verifyOtpResult.value = null
    }
}
