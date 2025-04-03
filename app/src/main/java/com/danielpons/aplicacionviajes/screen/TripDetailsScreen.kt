package com.danielpons.aplicacionviajes.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp  // Importación de dp
import androidx.compose.ui.unit.sp

@Composable
fun TripDetailsScreen(tripId: String?) {
    Column(modifier = Modifier.padding(16.dp)) { // Usando padding(16.dp)
        Text(text = "Detalles del Viaje", fontSize = 24.sp)
        Text(text = "ID: $tripId")
    }
}
