package com.example.kisansathi

import android.os.Build
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
    @RequiresApi(Build.VERSION_CODES.N)
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

    const val OTP_SCREEN ="OtpScreen"
    const val DASHBOARD_SCREEN ="dashboard"
}
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainAppContent() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppDestinations.AUTH_CHOICE_SCREEN) {
        composable(AppDestinations.AUTH_CHOICE_SCREEN) {

            AuthChoiceScreen(
                onLoginClick = {

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
                    navController.navigate(AppDestinations.DASHBOARD_SCREEN) {
                        popUpTo(AppDestinations.LOGIN_SCREEN) { inclusive = true }
                    }
                }) // Call your LoginScreen composable
        }
        composable(AppDestinations.REGISTER_SCREEN) {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    navController.navigate(AppDestinations.OTP_SCREEN) {
                        popUpTo(AppDestinations.REGISTER_SCREEN) { inclusive = true }
                    }
                }) // Call your LoginScreen composable
        }
//        composable("dashboard") { DashboardPage() }

        composable(AppDestinations.OTP_SCREEN) {
            OtpScreen(
                navController = navController,
                onOtpVerified = {
                    navController.navigate(AppDestinations.DASHBOARD_SCREEN) {
                        popUpTo(AppDestinations.OTP_SCREEN) { inclusive = true }
                    }
                }) // Call your LoginScreen composable
        }
        composable(AppDestinations.DASHBOARD_SCREEN) {
            DashboardPage()
        }

    }
}

