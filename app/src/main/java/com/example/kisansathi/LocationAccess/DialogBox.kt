package com.example.kisansathi.LocationAccess

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@Composable
fun LocationDialog(
    onDismiss: () -> Unit,
    onLocationSelected: (state: String, city: String, pincode: String) -> Unit
) {
    val context = LocalContext.current

    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // multiple permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            detectLocation(context) { s, c, p ->
                state = s
                city = c
                pincode = p
            }
        } else {
            Log.d("LocationDialog", "Location permission denied")
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Provide Location") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("State") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = pincode,
                    onValueChange = { pincode = it },
                    label = { Text("Pincode") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { permissionLauncher.launch(locationPermissions) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Auto Detect Location")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onLocationSelected(state, city, pincode)
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@SuppressLint("MissingPermission")
fun detectLocation(context: Context, onResult: (String, String, String) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val address = addresses?.firstOrNull()

                    val state = address?.adminArea ?: ""
                    val city = address?.locality ?: address?.subAdminArea ?: ""
                    val pincode = address?.postalCode ?: ""

                    withContext(Dispatchers.Main) {
                        onResult(state, city, pincode)
                    }
                } catch (e: Exception) {
                    Log.e("LocationDialog", "Geocoder failed: ${e.message}")
                    withContext(Dispatchers.Main) {
                        onResult("", "", "")
                    }
                }
            }
        } else {
            Log.d("LocationDialog", "lastLocation is null")
            onResult("", "", "")
            }
        }
}