package com.danielpons.aplicacionviajes.ui.theme.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


// Botón flotante para añadir un nuevo viaje
@Composable
fun AddTripButton(onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Añadir Viaje",
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.padding(end = 10.dp)
        )

        FloatingActionButton(onClick = onClick) {
            Icon(Icons.Default.Add, contentDescription = "Añadir Viaje", tint = Color.LightGray)
        }
    }
}
