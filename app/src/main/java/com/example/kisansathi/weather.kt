@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.People

@Composable

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(32.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { /* Open menu logic here */ }) {
//                Icon(
//                    imageVector = Icons.Filled.MoreVert, // Or your custom menu icon
//                    contentDescription = "Menu"
//                )
//            }
//        }
//    }
fun DashboardPage(
    userName: String = "Ravi",
    location: String = "Vellore, Tamil Nadu",
    temperature: String = "28°",
    condition: String = "Partly Cloudy",
    humidity: String = "65%",
    windSpeed: String = "12 km/h"
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Weather") },
                    navigationIcon = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Search */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Home") },
                            selected = true,
                            onClick = { }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.WbSunny, contentDescription = "Weather") },
                            label = { Text("Weather") },
                            selected = false,
                            onClick = { }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Mic, contentDescription = "Voice Chat") },
                            label = { Text("Voice Chat") },
                            selected = false,
                            onClick = { }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Market") },
                            label = { Text("Market") },
                            selected = false,
                            onClick = { }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.People, contentDescription = "Community") },
                            label = { Text("Community") },
                            selected = false,
                            onClick = { }
                        )
                    }

                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text("Welcome, $userName", style = MaterialTheme.typography.headlineMedium)
                Text("Check today's updates for your farm")

                Spacer(Modifier.height(16.dp))

                // Weather Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)), // green background
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Today's Weather", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Text("$temperature  $location", color = Color.White, style = MaterialTheme.typography.displayMedium)
                        Text(condition, color = Color.White)
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Humidity & Wind
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Humidity", style = MaterialTheme.typography.bodyMedium)
                        Text(humidity, style = MaterialTheme.typography.titleLarge)
                    }
                    Column {
                        Text("Wind Speed", style = MaterialTheme.typography.bodyMedium)
                        Text(windSpeed, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun previewFunction()
{
    DashboardPage()
}