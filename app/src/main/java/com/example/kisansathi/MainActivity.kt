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
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kisansathi.ui.theme.AuthChoiceScreen
import com.example.kisansathi.ForgotPasswordScreen

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
    const val SIGN_UP_SCREEN = "signup"
    const val OTP_SCREEN ="OtpScreen/{email}"
    const val DASHBOARD_SCREEN ="dashboard"
    const val FORGET_PASSWORD_SCREEN = "forget_password_screen"
    const val FORGOT_OTP_BASE_ROUTE = "forgot_otp_route" // Base route for OTP screen
    const val FORGOT_OTP_SCREEN_WITH_ARG = "$FORGOT_OTP_BASE_ROUTE/{email}" // Full route pattern
    const val NEW_PASSWORD_SCREEN = "new_password_screen"
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
                })
        }

        composable(AppDestinations.REGISTER_SCREEN) {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = { email ->
                    navController.navigate("OtpScreen/$email") {
                        popUpTo(AppDestinations.REGISTER_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        composable(AppDestinations.OTP_SCREEN) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpScreen(
                navController = navController,
                onOtpVerified = {
                    navController.navigate(AppDestinations.DASHBOARD_SCREEN) {
                        popUpTo(AppDestinations.OTP_SCREEN) { inclusive = true }
                    }
                },
                email = email
            )
        }



        composable(route = AppDestinations.FORGET_PASSWORD_SCREEN) { // Or use a constant like AppDestinations.FORGET_PASSWORD_SCREEN

            ForgotPasswordScreen(navController = navController)
        }
        composable(
            route = AppDestinations.FORGOT_OTP_SCREEN_WITH_ARG,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val emailArg = backStackEntry.arguments?.getString("email") ?: ""
            ForgotOtpScreen(
                navController = navController,
                email = emailArg,
                onVerifyClick = { resetToken ->
                    val newPasswordRoute = "${AppDestinations.NEW_PASSWORD_SCREEN}/$emailArg" +
                            (resetToken?.let { "/$it" } ?: "/no_token") // Handle null token
                    navController.navigate(newPasswordRoute) {
                        // Pop up to the OTP screen's base route to remove it from the back stack
                        popUpTo(AppDestinations.FORGOT_OTP_BASE_ROUTE) {
                            inclusive = true
                            // saveState = true // Consider if you need to save its state
                        }
                    }
                }
            )
        }
        // Example for NewPasswordScreen (ensure it can handle passed arguments)
        composable(
            route = "${AppDestinations.NEW_PASSWORD_SCREEN}/{email}/{resetToken}", // Define how it receives args
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("resetToken") { type = NavType.StringType } // Or nullable if token can be absent
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val token = backStackEntry.arguments?.getString("resetToken") // Will be "no_token" if null earlier

            if (email.isEmpty()) {
                Log.e("NavError", "Email argument missing for NewPasswordScreen")
                navController.popBackStack()
            } else {
                // NewPasswordScreen(navController = navController, email = email, resetToken = if (token == "no_token") null else token)
            }
        }



    }
}

//scamalert1507@gmail.com