package com.example.kisansathi

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kisansathi.ui.theme.DarkGreen
import androidx.compose.foundation.background
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.outlined.BugReport

import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun CategoryChip(label: String) {
    AssistChip(
        onClick = { /* Handle chip click */ },
        label = { Text(label) }
    )
}

sealed class DashboardScreen(val route: String, val label: String, val icon: ImageVector) {
    object Home : DashboardScreen("dashboard_home", "Home", Icons.Filled.Home)
    object Weather : DashboardScreen("dashboard_weather", "Weather",
        Icons.Filled.WbSunny)
    object VoiceChat : DashboardScreen("dashboard_voice_chat", "Voice Chat",
        Icons.Filled.Mic)
    object Market : DashboardScreen("dashboard_market", "Market",
        Icons.Filled.ShoppingCart)
    object PestControl : DashboardScreen("dashboard_pest_detect", "Pest",
        Icons.Outlined.BugReport)
}

val dashboardScreens = listOf(
    DashboardScreen.Home,
    DashboardScreen.Weather,
    DashboardScreen.VoiceChat,
    DashboardScreen.Market,
    DashboardScreen.PestControl
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboardLayout(
    appNavController: NavController // This is the NavController for overall app navigation (e.g., from login to dashboard)
) {
    val dashboardNavController =
        rememberNavController() // NavController for screens INSIDE the dashboard
    var currentDashboardRoute by remember { mutableStateOf(DashboardScreen.Home.route) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kisan Sathi") },
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
        //bottomBar = {
//            NavigationBar {
//                NavigationBar {
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                        label = { Text("Home") },
//                        selected = true,
//                        onClick = { }
//                    )
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.WbSunny, contentDescription = "Weather") },
//                        label = { Text("Weather") },
//                        selected = false,
//                        onClick = { }
//                    )
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.Mic, contentDescription = "Voice Chat") },
//                        label = { Text("Voice Chat") },
//                        selected = false,
//                        onClick = { }
//                    )
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Market") },
//                        label = { Text("Market") },
//                        selected = false,
//                        onClick = { }
//                    )
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Default.People, contentDescription = "Community") },
//                        label = { Text("Community") },
//                        selected = false,
//                        onClick = { }
//                    )
//                }
//
//            }
//        }
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = dashboardNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                dashboardScreens.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                screen.icon,
                                contentDescription = screen.label,
                                tint = if (currentRoute == screen.route) DarkGreen else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                screen.label,
                                color = if (currentRoute == screen.route) DarkGreen else Color.Gray
                            )
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            dashboardNavController.navigate(screen.route) {
                                popUpTo(dashboardNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
}
    ) { innerPadding ->
        // NavHost for the content of the dashboard screens
        NavHost(
            navController = dashboardNavController,
            startDestination = DashboardScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(DashboardScreen.Home.route) {
                // Update the current route for TopAppBar title and BottomNav selection
                LaunchedEffect(Unit) { currentDashboardRoute = DashboardScreen.Home.route }
                DashboardHomeScreen(appNavController) // Pass appNavController if needed for actions like logout
            }
            composable(DashboardScreen.Weather.route) {
                LaunchedEffect(Unit) { currentDashboardRoute = DashboardScreen.Weather.route }
                // Your WeatherScreen() composable from weather.kt
                // You might need to adapt it slightly if it had its own Scaffold before
                //DashboardWeatherScreen() // Pass relevant NavController if it needs to navigate further
            }
            composable(DashboardScreen.VoiceChat.route) {
                LaunchedEffect(Unit) { currentDashboardRoute = DashboardScreen.VoiceChat.route }
//                DashboardVoiceChatScreen()
            }
            composable(DashboardScreen.Market.route) {
                LaunchedEffect(Unit) { currentDashboardRoute = DashboardScreen.Market.route }
                DashboardMarketScreen()
            }
            composable(DashboardScreen.PestControl.route) {
                LaunchedEffect(Unit) { currentDashboardRoute = DashboardScreen.PestControl.route }
                DashboardCommunityScreen()
            }
        }
    }
}

@Composable
fun DashboardHomeScreen(appNavController: NavController) {
    Column(
        modifier = Modifier
            // .padding(screenSpecificPadding) // Example of additional padding
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), // Adjusted padding
            placeholder = { Text("Search farming tips, markets, or friends") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
        )

        // Category Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), // Adjusted padding
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryChip("All")
            CategoryChip("Crops")
            CategoryChip("Livestock")
            CategoryChip("Equipment")
        }

        // Banner Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), // Adjusted padding
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Use defaultElevation for M3
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFDFF2E1)))
            {

                Box(modifier=Modifier
                    .fillMaxSize()
                    .padding(12.dp)

                )
                {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("How to use app", fontWeight = FontWeight.Bold, color = DarkGreen)
                            Text("Learn about all the features of app", color = Gray,modifier= Modifier.padding(2.dp))

                            Spacer(modifier = Modifier.height(8.dp))
                            IconButton(
                                onClick = { /* TODO: handle click */ },

                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color(0xFF4CAF50), // green color
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                        }
                        Image(
                            painter = painterResource(id = R.drawable.hand),
                            contentDescription = "right image above background",
                            modifier = Modifier.size(120.dp),

                        )

                    }
                }

            }



        }

        Text("Today's Weather", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(12.dp))
        // Weather Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), // Adjusted padding
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Use defaultElevation for M3
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {


                    Icon(Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Akola, Maharashtra",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.Share, contentDescription = "Share",modifier = Modifier.size(24.dp))
                }
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    val iconRes = when (weather) {
                //        "Sunny" -> R.drawable.ic_sunny
                //        "Cloudy" -> R.drawable.ic_cloudy
                //        "Rainy" -> R.drawable.ic_rainy
                //        "Storm" -> R.drawable.ic_storm
                //        else -> R.drawable.ic_unknown
//                    }
                    Icon(Icons.Default.Bolt, contentDescription = "Weather")
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("28° Hazy Sunshine", fontWeight = FontWeight.Bold)
                        Text("High: 30° / Low: 15°")
                    }
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.Mic, contentDescription = "Voice") // Assuming Mic icon was for here
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Add some padding at the bottom if scrollable
    }
}

@Composable
fun DashboardWeatherScreen() {

    val userName = "Tanmay"
    val temperature = 23
    val location = "Akola"
    val condition = "Sunny"
    val humidity = "80%"
    val windSpeed = "5 mph"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Padding is applied by the outer Scaffold's NavHost
    ) {
        //Text("Today's Weather Updates", style = MaterialTheme.typography.headlineMedium)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Welcome, $userName", style = MaterialTheme.typography.headlineMedium)
            Text("Check today's updates for your farm")

            Spacer(Modifier.height(16.dp))

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

@Composable
fun DashboardMarketScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Market Prices", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun DashboardCommunityScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Community Forum", style = MaterialTheme.typography.headlineMedium)
        // Add community screen content
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainDashboardLayout() {
    MainDashboardLayout(rememberNavController())
}
