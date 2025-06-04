package com.danielpons.aplicacionviajes.screen.HomeScreen.TripScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.apptrip.model.Trip
import kotlinx.coroutines.launch

@Composable
fun EditTripScreen(navController: NavController, onDismiss: () -> Unit, trip: Trip) {
    var destination by remember { mutableStateOf(trip.name?: "") }
    var startDate by remember { mutableStateOf(trip.startDate?: "") }
    var endDate by remember { mutableStateOf(trip.endDate?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val tripRepository = TripRepository()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Fecha inicio (yyyy-MM-dd)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            IconButton(onClick = { showDatePicker(context) { startDate = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha inicio")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("Fecha fin (yyyy-MM-dd)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            IconButton(onClick = { showDatePicker(context) { endDate = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha fin")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                errorMessage = null
                if (destination.isEmpty() || !isValidDate(startDate) || !isValidDate(endDate)) {
                    errorMessage = "Por favor complete todos los campos correctamente."
                } else if (!isDateAfterOrEqualToday(startDate) || !isDateAfterOrEqualToday(endDate)) {
                    errorMessage = "Las fechas no pueden ser anteriores a hoy."
                } else if (!isEndDateAfterOrEqualStartDate(startDate, endDate)) {
                    errorMessage = "La fecha de fin no puede ser menor que la de inicio."
                } else {
                    println("Viaje actualizado: $destination, $startDate, $endDate")
                    onDismiss()
                    coroutineScope.launch {
                        try {
                            trip.id_trip?.let {
                                tripRepository.updateTrip(
                                    id = it,
                                    name = destination,
                                    startDate = startDate,
                                    endDate = endDate
                                )
                            }
                            navController.navigate("home")
                        } catch (e: Exception) {
                            errorMessage = "Error al actualizar el viaje: ${e.message}"
                        }
                    }


                }
            }
        ) {
            Text("Editar Viaje")
        }

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}