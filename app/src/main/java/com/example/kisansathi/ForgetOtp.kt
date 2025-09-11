package com.example.kisansathi

import android.widget.Toast // Import Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size // For CircularProgressIndicator
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions // For keyboard type
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator // Import CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // Import LaunchedEffect
import androidx.compose.runtime.collectAsState // Import collectAsState
import androidx.compose.runtime.getValue // Import getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue // Import setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester // For focus management
import androidx.compose.ui.focus.focusRequester // For focus management
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Import LocalContext
import androidx.compose.ui.platform.LocalFocusManager // For focus management
import androidx.compose.ui.text.input.KeyboardType // For keyboard type
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel // Import viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController // For preview
import com.example.kisansathi.ui.theme.viewmodel.ApiResult
import com.example.kisansathi.ui.theme.viewmodel.ForgotOtpViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotOtpScreen(
    navController: NavController, // Made NavController non-nullable for typical usage
    email: String,
    onVerifyClick: (resetToken: String?) -> Unit, // Callback passes the reset token
    forgotOtpViewModel: ForgotOtpViewModel = viewModel() // Inject ViewModel
) {
    val otpValues = remember { List(6) { mutableStateOf("") } }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    val verifyResult by forgotOtpViewModel.verifyOtpResult.collectAsState()

    LaunchedEffect(Unit) {
        focusRequesters.firstOrNull()?.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify OTP") },
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
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 32.dp), // Adjusted padding
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter OTP",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "OTP has been sent to $email",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                otpValues.forEachIndexed { index, state ->
                    OutlinedTextField(
                        value = state.value,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                state.value = newValue
                                if (newValue.isNotEmpty() && index < otpValues.size - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                } else if (newValue.isEmpty() && index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                                if (newValue.isNotEmpty() && index == otpValues.size - 1) {
                                    localFocusManager.clearFocus()
                                }
                            } else if (newValue.isEmpty()) {
                                state.value = ""
                                if (index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                            .focusRequester(focusRequesters[index]),
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        enabled = verifyResult !is ApiResult.Loading
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val otp = otpValues.joinToString("") { it.value }
                    if (otp.length == 6) {
                        forgotOtpViewModel.verifyOtp(email, otp)
                    } else {
                        Toast.makeText(context, "Please enter a 6-digit OTP", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = verifyResult !is ApiResult.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF31A05F),
                    contentColor = Color.White
                )
            ) {
                if (verifyResult is ApiResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("Verify OTP")
                }
            }

            // Handle API Result
            verifyResult?.let { result ->
                LaunchedEffect(result) {
                    when (result) {
                        is ApiResult.Success -> {
                            Toast.makeText(context, result.data.message, Toast.LENGTH_LONG).show()
                            onVerifyClick(result.data.resetToken) // Pass the reset token
                            forgotOtpViewModel.clearVerifyOtpResult()
                        }
                        is ApiResult.Error -> {
                            Toast.makeText(context, "OTP Verification Failed: ${result.message}", Toast.LENGTH_LONG).show()
                            forgotOtpViewModel.clearVerifyOtpResult()
                        }
                        is ApiResult.Loading -> {

                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewForgotOtpScreen() {
    val navController = rememberNavController()
    ForgotOtpScreen(
        navController = navController,
        email = "scamalert1507@gmail.com",
        onVerifyClick = {}
    )
}
