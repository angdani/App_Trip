package com.danielpons.aplicacionviajes.ui.theme.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.navigation.Screen
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home to Icons.Default.LocationOn, // Icono de avión para Home
        Screen.Profile to Icons.Default.Person,
        Screen.Notification to Icons.Default.Email// Icono de perfil de usuario para Profile
    )

    NavigationBar(
        modifier = Modifier.clip(MaterialTheme.shapes.medium) // Bordes redondeados
    ) {
        items.forEach { (screen, icon) ->
            NavigationBarItem(
                label = { Text(screen.title) },
                selected = navController.currentDestination?.route == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(icon, contentDescription = screen.title)
                }
            )
        }
    }
}
@Composable
@Preview
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}


