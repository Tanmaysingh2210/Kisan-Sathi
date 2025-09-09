package com.example.kisansathi.network


import com.example.kisansathi.ui.theme.models.LoginRequest
import com.example.kisansathi.ui.theme.models.LoginResponse
import com.google.android.gms.identitycredentials.RegistrationRequest
import com.google.android.gms.identitycredentials.RegistrationResponse
import retrofit2.Response // Use retrofit2.Response for full response details
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.kisansathi.ui.theme.models.registration_request
import retrofit2.http.GET
import retrofit2.http.Path // If you have path parameters like /users/{id}


interface ApiService {

    @POST("http://localhost:3000/api/auth/login/") // Matches your Express.js route
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("http://localhost:3000/api/auth/register")
    suspend fun registerUser(@Body request: registration_request): Response<RegistrationResponse>

    // Example for OTP verification if you have one
    // @POST("api/users/verify-otp")
    // suspend fun verifyOtp(@Body otpRequest: OtpRequest): Response<OtpResponse>

    // Example for fetching dashboard data
    // @GET("api/dashboard")
    // suspend fun getDashboardData(): Response<DashboardDataResponse>

    // Add other API calls here
}