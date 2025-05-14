package com.danielpons.aplicacionviajes.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Inicio")
    object Profile : Screen("profile", "Perfil")
    object Notification : Screen("notifications", "Notificaciones")
}