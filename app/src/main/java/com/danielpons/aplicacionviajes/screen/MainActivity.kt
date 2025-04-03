package com.danielpons.aplicacionviajes.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.danielpons.aplicacionviajes.screen.HomeScreen.HomeScreen
import com.danielpons.aplicacionviajes.ui.theme.AplicacionViajesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionViajesTheme {
                MyApp()
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("trip_details/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            TripDetailsScreen(tripId)
        }
        composable("add_trip") { AddTripScreen(navController, onDismiss = { navController.popBackStack() }) }
    }
}
