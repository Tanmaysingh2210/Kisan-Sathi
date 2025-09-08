package com.example.kisansathi.ui.theme.Modals

import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val sucess: Boolean,
    val message: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String?
)

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse
}