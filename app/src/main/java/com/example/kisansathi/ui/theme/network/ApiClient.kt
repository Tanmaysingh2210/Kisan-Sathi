package com.example.kisansathi.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    // Replace with your actual base URL
    // For emulator accessing PC localhost: "http://10.0.2.2:PORT/" (e.g., http://10.0.2.2:3000/)
    // For physical device on same network: "http://YOUR_PC_IP_ADDRESS:PORT/" (e.g., http://192.168.1.5:3000/)
    // For deployed server: "https://your-api-domain.com/"
    private const val BASE_URL = "http://10.0.2.2:3000/" // CHANGE THIS
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
