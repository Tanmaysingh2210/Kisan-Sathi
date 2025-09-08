package com.example.kisansathi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kisansathi.ui.theme.Dot


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun OtpScreen(navController: NavController?, email:String,onOtpVerified: () -> Unit ) {
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = List(6) { FocusRequester() }
    var errorMessage by remember { mutableStateOf<String?>(null)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.otpimg ),
            contentDescription= "Farmer Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(15.dp))

        )
        Spacer(modifier = Modifier.height(70.dp))

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Dot(isActive = false)
            Dot(isActive = false)
            Dot(isActive = false)
            Dot(isActive = true)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Enter the 6-digit code we sent to",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = email,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { input ->
                        if (input.length == 6) {
                            input.forEachIndexed { idx, ch ->
                                otpValues[idx] = ch.toString()
                            }
                            onOtpVerified()
                        }
                        else if (input.length <= 1) {
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


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Adds space between items
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Button(
                onClick = {


                },
                modifier = Modifier.weight(1f).width(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,      // 👈 background color
                    contentColor = Color.White
                )) {
                Text("Resend OTP")
            }

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
                modifier = Modifier.weight(1f).width(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF31A05F),      // 👈 background color
                    contentColor = Color.White         // 👈 text/icon color
                )

            ) {
                Text("Verify OTP")
            }

        }
    }

    // Focus first box on screen load
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}




@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOtpScreen() {
    OtpScreen(
        navController = rememberNavController(),
        email = "charlescurtis@myownpersonaldomain.com",
        onOtpVerified = {}
    )
}







