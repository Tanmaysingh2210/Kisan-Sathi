package com.example.kisansathi.ui.theme.models
//
//import retrofit2.http.Body
//import retrofit2.http.POST
//
//data class RegisterRequest(
//    val name: String,
//    val email: String,
//    val password: String
//)
//
//data class RegisterResponse(
//    val success: Boolean,
//    val message: String
//)
//
//data class LoginRequest(
//    val email: String,
//    val password: String
//)
//
//data class LoginResponse(
//    val success: Boolean,
//    val token: String?
//)
//


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
    val username: String,
    val email: String,
    val password: String
)

data class registration_response(
    val userId: String,
    val message: String
)
