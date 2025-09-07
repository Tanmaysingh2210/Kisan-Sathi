package com.example.kisansathi

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kisansathi.ui.theme.AuthChoiceScreen
import com.example.kisansathi.ui.theme.KisanSathiTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable Splash Screen
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            KisanSathiTheme {
                var showContent by remember { mutableStateOf(false) }

                // Control splash duration
                LaunchedEffect(Unit) {
                    delay(2000) // 2 seconds delay
                    showContent = true
                }

                if (showContent) {
                    MainAppContent()
                }
            }
        }
    }
}

@Composable
fun MainAppContent() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "choices") {
        composable("choices") { AuthChoiceScreen ({ navController.navigate("login") }, { navController.navigate("register") }) }
        composable("dashboard") { DashboardPage() }
    }
}

