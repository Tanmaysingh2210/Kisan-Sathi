
package com.example.kisansathi.ui.theme.viewmodel

import com.example.kisansathi.ui.theme.repository.AuthRepository
import com.example.kisansathi.ui.theme.viewmodel.ApiResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisansathi.ui.theme.models.registration_request
import com.example.kisansathi.ui.theme.models.registration_response
//import com.google.android.gms.identitycredentials.RegistrationRequest
import com.example.kisansathi.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() { // In a real app, inject AuthRepository


    private val authRepository = AuthRepository(RetrofitInstance.api)

    private val _registrationResult = MutableStateFlow<ApiResult<registration_response>?>(null)
    val registrationResult: StateFlow<ApiResult<registration_response>?> = _registrationResult

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationResult.value = ApiResult.Loading
            try {
                // Assuming your RegistrationRequest model has 'name', 'email', 'password'
                // Adjust if your model is different (e.g., uses 'username' instead of 'name')
                val request = registration_request(
                    name = name,
                    email = email,
                    password = password
                )
                val response = authRepository.registerUser(request)

//                if (response.isSuccessful && response.body() != null) {
//                    _registrationResult.value = ApiResult.Success(response.body()!!)
//                } else {
//                    val errorMsg = response.errorBody()?.string() ?: response.message() ?: "Registration failed"
//                    _registrationResult.value = ApiResult.Error(errorMsg, response.code())
//                }
                if (response.isSuccessful) {
                    val responseBody = response.body() as com.example.kisansathi.ui.theme.models.registration_response
                    if (responseBody != null) {
                        _registrationResult.value = ApiResult.Success(responseBody) // No '!!' needed here due to the check
                    } else {
                        // This case means response was successful (e.g., 200 OK) BUT the body was null.
                        // This is unusual for a registration response but possible.
                        // Handle as an error or a specific success-with-no-data case.
                        _registrationResult.value = ApiResult.Error("Registration successful but no data returned", response.code())
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: response.message() ?: "Registration failed"
                    _registrationResult.value = ApiResult.Error(errorMsg, response.code())
                }
            } catch (e: Exception) {
                _registrationResult.value = ApiResult.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    // Optional: Call this to reset the state after it's been handled in the UI
    fun clearRegistrationResult() {
        _registrationResult.value = null
    }
}
