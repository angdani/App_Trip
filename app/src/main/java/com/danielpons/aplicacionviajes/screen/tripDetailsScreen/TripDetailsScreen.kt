package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Event
import androidx.navigation.NavController
import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.EventsSection
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.TaskListSection

@Composable
fun PointsOfInterestSection(pointsOfInterest: List<String>) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Puntos de interés:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (pointsOfInterest.isEmpty()) {
                Text(
                    text = "No hay puntos de interés disponibles.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                pointsOfInterest.forEach { point ->
                    Text(
                        text = "- $point",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

// Función para mostrar la lista de tareas

// Función para mostrar el título del viaje
@Composable
fun TripTitle(tripTitle: String, onShareClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tripTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Compartir viaje"
            )
        }
    }
}


// Función para mostrar la fecha del viaje
@Composable
fun TripDate(tripDate: String) {
    Text(
        text = "Día del viaje: $tripDate",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}


// Pantalla principal que utiliza las funciones divididas


@Composable
fun BackToHomeButton(navController: NavController) {
    Button(onClick = { navController.navigate("home") }) {
        Text("Volver a Home")
    }
}


@Composable
fun TripDetailsScreen(
    tripTitle: String,
    events: List<Event>,
    pointsOfInterest: List<String>,
    tasks: List<Task>,
    onTaskCheckedChange: (Int, Boolean) -> Unit,
    navController: NavController
) {
    Column(modifier = Modifier.padding(16.dp)) {
TripTitle(tripTitle, onShareClick = {
    navController.navigate("send_notification")
})
        Spacer(modifier = Modifier.height(25.dp))
        EventsSection(events)
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PointsOfInterestSection(pointsOfInterest)
            Spacer(modifier = Modifier.width(16.dp))
            TaskListSection(tasks, onTaskCheckedChange)
        }
    }

}

