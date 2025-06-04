package com.danielpons.aplicacionviajes.screen.HomeScreen.TripScreen

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.apptrip.model.Trip
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


fun isValidDate(date: String): Boolean {
    return date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
}

fun isDateAfterOrEqualToday(date: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val inputDate = sdf.parse(date)
        val today = sdf.parse(sdf.format(Date()))
        !inputDate.before(today)
    } catch (e: Exception) {
        false
    }
}

fun isEndDateAfterOrEqualStartDate(startDate: String, endDate: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = sdf.parse(startDate)
        val end = sdf.parse(endDate)
        !end.before(start)
    } catch (e: Exception) {
        false
    }
}

fun showDatePicker(context: android.content.Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val date = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@Composable
fun AddTripScreen(navController: NavController, onDismiss: () -> Unit) {
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val tripRepository = TripRepository()
    var trip: Trip
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                    //  guarda el viaje en la base de datos
                    trip = Trip(
                        id_trip = 0,
                        name = destination,
                        startDate = startDate,
                        endDate = endDate,
                        description = "",
                        cover_image_url = null,
                        status = Trip.TripStatus.PLANNED
                    )

                    coroutineScope.launch {
                        tripRepository.addTrip(trip)
                        println("Viaje guardado: $destination, $startDate, $endDate")
                        onDismiss()
                    }

                }
            }
        ) {
            Text("Guardar Viaje")
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