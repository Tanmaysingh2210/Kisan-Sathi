
package com.example.kisansathi.ui.theme.viewmodel

import com.example.kisansathi.ui.theme.repository.AuthRepository
import com.example.kisansathi.ui.theme.viewmodel.ApiResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisansathi.ui.theme.models.registration_request
import com.example.kisansathi.ui.theme.models.registration_response
//import com.google.android.gms.identitycredentials.RegistrationRequest
import com.example.kisansathi.network.RetrofitInstance
import com.example.kisansathi.ui.theme.models.LoginRequest
import com.example.kisansathi.ui.theme.models.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() { // In a real app, inject AuthRepository


    private val authRepository = AuthRepository(RetrofitInstance.api)

    private val _loginResult = MutableStateFlow<ApiResult<LoginResponse>?>(null)
    val loginResult: StateFlow<ApiResult<LoginResponse>?> = _loginResult

    fun loginUser( email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = ApiResult.Loading
            try {

                val request = LoginRequest(
                    email = email,
                    password = password
                )
                val response = authRepository.loginUser(request)

//                if (response.isSuccessful && response.body() != null) {
//                    _registrationResult.value = ApiResult.Success(response.body()!!)
//                } else {
//                    val errorMsg = response.errorBody()?.string() ?: response.message() ?: "Registration failed"
//                    _registrationResult.value = ApiResult.Error(errorMsg, response.code())
//                }
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loginResult.value = ApiResult.Success(responseBody) // No '!!' needed here due to the check
                    } else {
                        // This case means response was successful (e.g., 200 OK) BUT the body was null.
                        // This is unusual for a registration response but possible.
                        // Handle as an error or a specific success-with-no-data case.
                        _loginResult.value = ApiResult.Error("Login successful but no data returned", response.code())
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: response.message() ?: "Login failed"
                    _loginResult.value = ApiResult.Error(errorMsg, response.code())
                }
            } catch (e: Exception) {
                _loginResult.value = ApiResult.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    // Optional: Call this to reset the state after it's been handled in the UI
    fun clearLoginResult() {
        _loginResult.value = null
    }
}
