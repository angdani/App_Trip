package com.danielpons.aplicacionviajes.ui.theme.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.danielpons.aplicacionviajes.navigation.Screen
import io.ktor.websocket.Frame

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Profile
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                label = { Frame.Text(screen.title) },
                selected = navController.currentDestination?.route == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    // Puedes agregar íconos aquí si lo deseas
                    Icon(Icons.Default.Home, contentDescription = screen.title)
                }
            )
        }
    }
}