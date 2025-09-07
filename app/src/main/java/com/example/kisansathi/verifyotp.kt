package com.example.kisansathi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun OtpScreen(navController: NavController?=null,onOtpVerified: () -> Unit) {
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = List(6) { FocusRequester() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter OTP", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { input ->
                        if (input.length <= 1) {
                            otpValues[index] = input

                            if (input.isNotEmpty()) {
                                if (index < 5) {
                                    // ✅ Move pointer to next box
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    // ✅ Last digit entered → auto verify
                                    val enteredOtp = otpValues.joinToString("")
                                    if (enteredOtp == "123456") {
                                        errorMessage = null
                                        onOtpVerified()
                                    } else {
                                        errorMessage = "Invalid OTP, try again"
                                        otpValues.replaceAll { "" }
                                        focusRequesters[0].requestFocus()
                                    }
                                }
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        errorMessage?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val enteredOtp = otpValues.joinToString("")
                if (enteredOtp == "123456") {
                    errorMessage = null
                    onOtpVerified()
                } else {
                    errorMessage = "Invalid OTP, try again"
                    otpValues.replaceAll { "" }
                    focusRequesters[0].requestFocus()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify OTP")
        }
    }

    // Focus first box on screen load
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}



