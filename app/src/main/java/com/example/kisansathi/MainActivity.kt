package com.example.kisansathi

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Bundle
import android.util.Log
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

object AppDestinations {
    const val AUTH_CHOICE_SCREEN = "AuthChoiceScreen"
    const val LOGIN_SCREEN = "LoginScreen"
    const val REGISTER_SCREEN = "RegisterScreen"
    const val SIGN_UP_SCREEN = "signup" // If you also have a sign-up screen
    // ... other destinations
}
@Composable
fun MainAppContent() {
    Log.d("NavigationDebug", "MainAppContent: Composing")
    val navController = rememberNavController()
    Log.d("NavigationDebug", "MainAppContent: NavHost startDestination = ${AppDestinations.AUTH_CHOICE_SCREEN}")
    NavHost(navController = navController, startDestination = AppDestinations.AUTH_CHOICE_SCREEN) {
        composable(AppDestinations.AUTH_CHOICE_SCREEN) {
            Log.d("NavigationDebug", "NavHost: Composing AUTH_CHOICE_SCREEN")
            AuthChoiceScreen(
                onLoginClick = {
                    Log.d("NavigationDebug", "NavHost: Composing LOGIN_SCREEN")
                    navController.navigate(AppDestinations.LOGIN_SCREEN)
                },
                onSignUpClick = {
                    navController.navigate(AppDestinations.REGISTER_SCREEN)
                }
            )
        }
        composable(AppDestinations.LOGIN_SCREEN) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo(AppDestinations.LOGIN_SCREEN) { inclusive = true }
                    }
                }) // Call your LoginScreen composable
        }
        composable(AppDestinations.REGISTER_SCREEN) {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo(AppDestinations.LOGIN_SCREEN) { inclusive = true }
                    }
                }) // Call your LoginScreen composable
        }
//        composable("dashboard") { DashboardPage() }
    }
}

