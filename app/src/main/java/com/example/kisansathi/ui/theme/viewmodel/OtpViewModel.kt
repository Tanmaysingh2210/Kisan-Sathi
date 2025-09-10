package com.example.kisansathi.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisansathi.ui.theme.models.verify_otp_request
import com.example.kisansathi.ui.theme.models.verify_otp_response
import com.example.kisansathi.ui.theme.repository.AuthRepository
import com.example.kisansathi.network.RetrofitInstance // Or inject repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Assuming ApiResult is defined (Success, Error, Loading)

class OtpViewModel : ViewModel() { // In a real app, consider injecting AuthRepository

    private val authRepository = AuthRepository(RetrofitInstance.api) // Simple instantiation

    private val _otpVerificationResult = MutableStateFlow<ApiResult<verify_otp_response>?>(null)
    val otpVerificationResult: StateFlow<ApiResult<verify_otp_response>?> = _otpVerificationResult

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _otpVerificationResult.value = ApiResult.Loading
            try {
                val request = verify_otp_request(email = email, otp = otp)
                val response = authRepository.verifyOtp(request)

                if (response.isSuccessful && response.body() != null) {
                    _otpVerificationResult.value = ApiResult.Success(response.body()!!)
                    // If a token is returned and verification implies login, save the token here
                } else {
                    val errorMsg = response.errorBody()?.string() ?: response.message() ?: "OTP verification failed"
                    _otpVerificationResult.value = ApiResult.Error(errorMsg, response.code())
                }
            } catch (e: Exception) {
                _otpVerificationResult.value = ApiResult.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun clearOtpVerificationResult() {
        _otpVerificationResult.value = null
    }
}