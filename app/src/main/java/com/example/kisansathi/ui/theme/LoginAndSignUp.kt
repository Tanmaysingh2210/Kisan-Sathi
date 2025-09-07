package com.example.kisansathi.ui.theme
//import android.Color.kt
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kisansathi.R

@Composable
fun AuthChoiceScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.auth ), // replace with your drawable
            contentDescription= "Farmer Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(15.dp))

        )
        Spacer(modifier = Modifier.height(70.dp))

        Text(
            "Welcome To Kisan Sathi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Empowering every farmer with knowledge, tools, and guidance to grow smarter and live better.",
            fontSize = 18.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        // Pagination dots
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Dot(isActive = true)
            Dot(isActive = false)
            Dot(isActive = false)
            Dot(isActive = false)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Adds space between items
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.weight(1f).width(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,      // 👈 background color
                    contentColor = Color.White         // 👈 text/icon color
            )


            ) {
                Text("Login", color = Color.Black, fontWeight = FontWeight.Bold)
            }
//                Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSignUpClick,
                modifier = Modifier.weight(1f).width(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF31A05F),      // 👈 background color
                    contentColor = Color.White         // 👈 text/icon color
                )
            ) {
                Text("Sign Up")
            }


        }
    }
}

@Composable
fun Dot(isActive: Boolean) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAuthChoiceScreen() {
    AuthChoiceScreen(
        onLoginClick = {},
        onSignUpClick = {}
    )
}
