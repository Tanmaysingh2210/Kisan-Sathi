package com.example.kisansathi.LocationAccess


import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kisansathi.AppDestinations

@Composable
fun AppFlow(appNavController: NavController) {
    val flowNavController = rememberNavController()

    NavHost(
        navController = flowNavController,
        startDestination = "location"
    ) {
        composable("location") {
            LocationDialog(
                onDismiss = {
                    appNavController.navigate(AppDestinations.DASHBOARD_MAIN_LAYOUT) {
                        popUpTo(AppDestinations.AUTH_CHOICE_SCREEN) { inclusive = false }
                    }
                },
                onLocationSelected = { state, city, pincode ->
                    // ✅ Save location if needed (ViewModel/Preferences)
                    appNavController.navigate(AppDestinations.DASHBOARD_MAIN_LAYOUT) {
                        popUpTo(AppDestinations.AUTH_CHOICE_SCREEN) { inclusive = false }
                    }
                }
            )
            }
        }
}


