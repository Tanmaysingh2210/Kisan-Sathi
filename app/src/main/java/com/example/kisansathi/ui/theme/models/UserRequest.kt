package com.example.kisansathi.ui.theme.models



data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val userId: String,
    val token: String, // If your backend sends a JWT token
    val message: String
)

data class registration_request(
    val name: String,
    val email: String,
    val password: String
)

data class registration_response(
    val userId: String,
    val message: String
)

data class verify_otp_request(
    val email: String,
    val otp: String
)

data class verify_otp_response(
    val message: String,
    val token: String?= null
)

data class ForgetPasswordRequest(
    val email: String
)

data class ForgetPasswordResponse(
    val message: String,
)


data class VerifyForgotOtpRequest(
    val email: String,
    val otp: String
)

data class VerifyForgotOtpResponse(
    val message: String,
    val resetToken: String? = null
)