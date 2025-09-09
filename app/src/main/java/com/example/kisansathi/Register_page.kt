package com.example.kisansathi

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kisansathi.ui.theme.AuthChoiceScreen
import com.example.kisansathi.ui.theme.Dot
import com.example.kisansathi.ui.theme.viewmodel.ApiResult
import com.example.kisansathi.ui.theme.viewmodel.RegisterViewModel




@Composable
fun RegisterScreen( navController: NavController?=null, onRegisterSuccess: (String) -> Unit,
                    registerViewModel: RegisterViewModel = viewModel()
)
{
    var name by remember {mutableStateOf(value= "")}
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current // For Toasts


    val registrationResult by registerViewModel.registrationResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Image(
            painter = painterResource(id = R.drawable.register ), // replace with your drawable
            contentDescription= "Farmer Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(15.dp))

        )
        Spacer(modifier = Modifier.height(70.dp))

        Text("Join the future of farming" , style = MaterialTheme.typography.headlineMedium,fontWeight = FontWeight.Bold)


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Together, let's cultivate success - from your fields to your future.",
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            PagerDot(isActive = false)
            PagerDot(isActive = true)
            PagerDot(isActive = false)
            PagerDot(isActive = false)
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = registrationResult !is ApiResult.Loading
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email id") },
            modifier = Modifier.fillMaxWidth(),
            enabled = registrationResult !is ApiResult.Loading
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            enabled = registrationResult !is ApiResult.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if( email.isNotBlank() && password.isNotBlank() && name.isNotBlank() ){
                    registerViewModel.registerUser(name, email, password)
                }else {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = registrationResult !is ApiResult.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF31A05F),      // 👈 background color
                contentColor = Color.White         // 👈 text/icon color
            )
        ) {
            if (registrationResult is ApiResult.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Sign Up")
            }
        }
        registrationResult?.let { result ->


            LaunchedEffect(result) { // Use LaunchedEffect to handle side effects like Toasts or navigation
                when (result) {


                    is ApiResult.Success -> {
                        Toast.makeText(context, "Registration Successful: ${result.data.message}", Toast.LENGTH_LONG).show()
                        onRegisterSuccess(email) // Call the success lambda to navigate
                        registerViewModel.clearRegistrationResult() // Reset state
                    }
                    is ApiResult.Error -> {
                        Toast.makeText(context, "Registration Failed: ${result.message}", Toast.LENGTH_LONG).show()
                        registerViewModel.clearRegistrationResult() // Reset state
                    }
                    is ApiResult.Loading -> {
                        // UI shows loading indicator in the button
                    }
                }
            }
        }
    }
}



@Composable
fun PagerDot(isActive: Boolean) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(8.dp)
            .background(
                color = if (isActive) Color.Black else Color.LightGray,
                shape = CircleShape
            )
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewAuthChoiceScreen() {
//    RegisterScreen(),
//    onRegisterSuccess = {  }
//}


