package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.danielpons.aplicacionviajes.data.model.Event
import kotlinx.datetime.Clock

@Composable
fun AddEventScreen(navController: NavController, tripId: Int) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título del evento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Ubicación") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Costo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotEmpty() && cost.toDoubleOrNull() != null) {
                    val newEvent = Event(
                        id_trip = tripId,
                        title = title,
                        description = description,
                        start_datetime = Clock.System.now(),
                        location = location,
                        cost = cost.toDouble(),
                        cost_currency = "USD"
                    )
                    // Aquí puedes guardar el evento en tu repositorio o base de datos
                    navController.popBackStack() // Regresa a la pantalla anterior
                } else {
                    isFormValid = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Evento")
        }

        if (!isFormValid) {
            Text(
                text = "Por favor, complete los campos correctamente.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddEventScreen() {
    val navController = TestNavHostController(LocalContext.current)
    AddEventScreen(navController = navController, tripId = 101)
}