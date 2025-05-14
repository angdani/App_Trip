package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Event
import androidx.navigation.NavController

@Composable
fun PointsOfInterestSection(pointsOfInterest: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
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
@Composable
fun TaskListSection(tasks: List<Pair<String, Boolean>>, onTaskCheckedChange: (Int, Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Lista de tareas:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (tasks.isEmpty()) {
                Text(
                    text = "No hay tareas pendientes.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                tasks.forEachIndexed { index, (task, isChecked) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { onTaskCheckedChange(index, it) }
                        )
                        Text(
                            text = task,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
// Función para mostrar el título del viaje
@Composable
fun TripTitle(tripTitle: String) {
    Text(
        text = tripTitle,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

// Función para mostrar la sección de eventos
@Composable
fun EventsSection(events: List<Event>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Eventos:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (events.isEmpty()) {
                Text(
                    text = "No hay eventos disponibles.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(events) { event ->
                        Card(
                            modifier = Modifier
                                .width(250.dp)
                                .height(150.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    maxLines = 1
                                )
                                event.description?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 2
                                    )
                                }
                                Text(
                                    text = "Hora: ${event.start_datetime}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "Costo: ${event.cost?.let { "$$it ${event.cost_currency}" } ?: "Gratis"}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Función para mostrar la sección de gastos totales
@Composable
fun TotalExpensesSection(totalExpenses: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Gastos totales:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "$${"%.2f".format(totalExpenses)}",
                style = MaterialTheme.typography.bodyLarge
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

// Función para mostrar el progreso del viaje
@Composable
fun TripProgress(budgetPercentage: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Progreso del viaje",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            LinearProgressIndicator(
                progress = budgetPercentage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Avión",
                modifier = Modifier
                    .padding(start = (budgetPercentage * 300).dp)
                    .size(24.dp)
            )
        }
        Text(
            text = "${(budgetPercentage * 100).toInt()}% completado",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}






// Pantalla principal que utiliza las funciones divididas
@Composable
fun TripOverviewScreen(
    tripTitle: String,
    events: List<Event>,
    totalExpenses: Double,
    tripDate: String,
    budgetPercentage: Float,
    pointsOfInterest: List<String>,
    tasks: List<Pair<String, Boolean>>,
    onTaskCheckedChange: (Int, Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TripTitle(tripTitle)
        EventsSection(events)
        TotalExpensesSection(totalExpenses)
        TripDate(tripDate)
        TripProgress(budgetPercentage)
        PointsOfInterestSection(pointsOfInterest)
        TaskListSection(tasks, onTaskCheckedChange)
    }
}


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
    tasks: List<Pair<String, Boolean>>,
    onTaskCheckedChange: (Int, Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TripTitle(tripTitle)
        EventsSection(events)
        PointsOfInterestSection(pointsOfInterest)
        TaskListSection(tasks, onTaskCheckedChange)
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewTripDetailsScreen() {
    TripDetailsScreen(
        tripTitle = "Viaje a la montaña",
        events = listOf(
            Event(
                id_event = 1,
                id_trip = 1,
                title = "Excursión",
                description = "Caminata por el sendero principal.",
                start_datetime = kotlinx.datetime.Instant.parse("2023-11-10T09:00:00Z"),
                cost = 0.0
            ),
            Event(
                id_event = 2,
                id_trip = 1,
                title = "Cena en refugio",
                description = "Cena grupal en el refugio de montaña.",
                start_datetime = kotlinx.datetime.Instant.parse("2023-11-10T19:00:00Z"),
                cost = 30.0
            )
        ),
        pointsOfInterest = listOf("Mirador", "Cascada", "Refugio"),
        tasks = listOf(
            "Preparar mochila" to true,
            "Comprar snacks" to false,
            "Revisar clima" to true
        ),
        onTaskCheckedChange = { _, _ -> }
    )
}