package com.example.kisansathi.viewmodel // Or your chosen package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kisansathi.ui.theme.models.ForgetPasswordRequest
import com.example.kisansathi.ui.theme.models.ForgetPasswordResponse
import com.example.kisansathi.ui.theme.repository.AuthRepository
import com.example.kisansathi.network.RetrofitInstance // Assuming you have this for easy instance access
import com.example.kisansathi.ui.theme.viewmodel.ApiResult
// Or inject AuthRepository using Hilt/Koin for better testability
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Assuming ApiResult sealed class is already defined (Success, Error, Loading)
// sealed class ApiResult<out T> {
//     data class Success<out T>(val data: T) : ApiResult<T>()
//     data class Error(val message: String, val statusCode: Int? = null) : ApiResult<Nothing>()
//     object Loading : ApiResult<Nothing>()
// }

class ForgetPasswordViewModel : ViewModel() { // For production, inject AuthRepository

    // If not using DI, instantiate directly (less ideal for testing)
    private val authRepository = AuthRepository(RetrofitInstance.api)

    private val _resetRequestResult = MutableStateFlow<ApiResult<ForgetPasswordResponse>?>(null)
    val resetRequestResult: StateFlow<ApiResult<ForgetPasswordResponse>?> = _resetRequestResult

    fun sendPasswordResetRequest(email: String) {
        viewModelScope.launch {
            _resetRequestResult.value = ApiResult.Loading
            try {
                val request = ForgetPasswordRequest(email = email)
                val response = authRepository.requestPasswordReset(request)

                if (response.isSuccessful && response.body() != null) {
                    _resetRequestResult.value = ApiResult.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (!errorBody.isNullOrBlank()) {
                        // Attempt to parse error JSON if your backend sends structured errors
                        // For simplicity, using the raw string here
                        errorBody
                    } else {
                        response.message() ?: "Failed to send reset request"
                    }
                    _resetRequestResult.value = ApiResult.Error(errorMessage, response.code())
                }
            } catch (e: Exception) {
                _resetRequestResult.value = ApiResult.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    // Call this from your UI to clear the result after it has been handled
    fun clearResetRequestResult() {
        _resetRequestResult.value = null
    }
}
