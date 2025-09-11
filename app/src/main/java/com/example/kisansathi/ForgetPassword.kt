package com.example.kisansathi


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kisansathi.ui.theme.viewmodel.ApiResult
import com.example.kisansathi.viewmodel.ForgetPasswordViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    forgetPasswordViewModel: ForgetPasswordViewModel = viewModel()
)
{
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    val resetResult by forgetPasswordViewModel.resetRequestResult.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding from Scaffold
                .padding(16.dp), // Additional screen padding
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Forgot Your Password?", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Enter your email to receive an OTP.")
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField( // Using OutlinedTextField for a common look
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = resetResult !is ApiResult.Loading // Disable input while loading
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotBlank()) {
                        forgetPasswordViewModel.sendPasswordResetRequest(email)
                    } else {
                        Toast.makeText(context, "Please enter your email address.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = resetResult !is ApiResult.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF31A05F),
                    contentColor = Color.White
                )
            ) {
                if (resetResult is ApiResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("Send OTP")
                }
            }

            // Handle API Result (Success, Error messages, Navigation)
            resetResult?.let { result ->
                LaunchedEffect(result) {
                    when (result) {
                        is ApiResult.Success -> {
                            Toast.makeText(context, result.data.message, Toast.LENGTH_LONG).show()
                            // Navigate to OTP verification screen, passing the email
                            navController.navigate("otpVerification/${email}") {
                                // Optional: popUpTo this screen if you don't want to return here on back press from OTP
                                // popUpTo("forgot_password_route_name") { inclusive = true }
                            }
                            forgetPasswordViewModel.clearResetRequestResult() // Reset state
                        }
                        is ApiResult.Error -> {
                            Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                            forgetPasswordViewModel.clearResetRequestResult() // Reset state
                        }
                        is ApiResult.Loading -> {
                            // UI shows loading indicator in the button
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewForgotPasswordScreen() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController )
}