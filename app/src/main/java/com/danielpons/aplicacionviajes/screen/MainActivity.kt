package com.danielpons.aplicacionviajes.screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.screen.HomeScreen.HomeScreen
import com.danielpons.aplicacionviajes.screen.HomeScreen.ProfileScreen
import com.danielpons.aplicacionviajes.screen.HomeScreen.TripScreen.AddTripScreen
import com.danielpons.aplicacionviajes.screen.HomeScreen.EditUserScreen
import com.danielpons.aplicacionviajes.screen.loginScreen.LoginScreen
import com.danielpons.aplicacionviajes.screen.loginScreen.RegisterUserScreen
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.SendNotificationScreen
import com.danielpons.aplicacionviajes.ui.theme.AplicacionViajesTheme
import com.danielpons.aplicacionviajes.ui.theme.Components.pagerState.PagerScreen


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

@Composable
fun MyApp() {
    val navController = rememberNavController()

val startDestination = if (UserSession.userId?.toString().isNullOrEmpty()) "login" else "home"
    // Actualiza el userId después del inicio de sesión
    NavHost(navController, startDestination = startDestination) {
        composable("home") { HomeScreen(navController) }
        composable("login") {
            LoginScreen(navController, onLoginSuccess = { // Actualiza el userId después del inicio de sesión
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("register_user") {
            RegisterUserScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("notifications") {
            NotificationScreen(navController)
        }
        composable("add_trip") {
            AddTripScreen(navController, onDismiss = { navController.popBackStack() })        }

        composable("trip_pager/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            PagerScreen(navController) // Por ahora no usas tripId
        }
        composable("send_notification") {
           SendNotificationScreen(navController)
        }
        composable("edit_userScreen") {
            EditUserScreen(navController) }
    }
}