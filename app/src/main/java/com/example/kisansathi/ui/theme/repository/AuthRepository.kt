package com.example.kisansathi.ui.theme.repository

import com.example.kisansathi.network.ApiService
import com.example.kisansathi.ui.theme.models.ForgetPasswordRequest
import com.example.kisansathi.ui.theme.models.ForgetPasswordResponse
import com.example.kisansathi.ui.theme.models.LoginRequest
import com.example.kisansathi.ui.theme.models.LoginResponse
import com.example.kisansathi.ui.theme.models.VerifyForgotOtpRequest
import com.example.kisansathi.ui.theme.models.VerifyForgotOtpResponse
import com.example.kisansathi.ui.theme.models.registration_request
import com.example.kisansathi.ui.theme.models.registration_response
import com.example.kisansathi.ui.theme.models.verify_otp_request
import com.example.kisansathi.ui.theme.models.verify_otp_response
//import com.google.android.gms.identitycredentials.RegistrationResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(loginRequest)
    }

    suspend fun registerUser(request: registration_request):  Response<registration_response> {
        return apiService.registerUser(request)
    }

    suspend fun verifyOtp(otpVerificationRequest: verify_otp_request): Response<verify_otp_response> {
        return apiService.verifyOtp((otpVerificationRequest))
    }
    suspend fun requestPasswordReset(forgetPasswordRequest: ForgetPasswordRequest): Response<ForgetPasswordResponse> {
        return apiService.forgotPass(forgetPasswordRequest)
    }


    suspend fun verifyForgotPasswordOtp(request: VerifyForgotOtpRequest): Response<VerifyForgotOtpResponse> {
        return apiService.verifyForgotPasswordOtp(request)
    }
    // Add other methods that call ApiService functions
}

