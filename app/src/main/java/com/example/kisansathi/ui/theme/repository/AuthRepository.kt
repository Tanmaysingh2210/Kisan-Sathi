package com.example.kisansathi.ui.theme.repository

import com.example.kisansathi.network.ApiService
import com.example.kisansathi.ui.theme.models.LoginRequest
import com.example.kisansathi.ui.theme.models.LoginResponse
import com.example.kisansathi.ui.theme.models.registration_request
import com.google.android.gms.identitycredentials.RegistrationResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(loginRequest)
    }

    suspend fun registerUser(request: registration_request):  Response<RegistrationResponse> {
        return apiService.registerUser(request)
    }
    // Add other methods that call ApiService functions
}

