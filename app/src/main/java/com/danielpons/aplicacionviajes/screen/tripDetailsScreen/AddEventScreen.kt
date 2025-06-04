package com.danielpons.aplicacionviajes.screen.tripDetailsScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import java.util.Calendar
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.text.input.KeyboardType
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Event
import java.text.SimpleDateFormat
import java.util.*
import com.danielpons.aplicacionviajes.data.repository.EventRepository
import kotlinx.coroutines.launch


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

fun showTimePicker(context: android.content.Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val time = "%02d:%02d".format(hourOfDay, minute)
            onTimeSelected(time)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).show()
}
@Composable
fun AddEventScreen(onDismiss: () -> Unit) {
    var eventName by remember { mutableStateOf("") }
    var descriptionName by remember { mutableStateOf("") }
    var eventStartDate by remember { mutableStateOf("") }
    var eventStartTime by remember { mutableStateOf("") }
    var eventEndDate by remember { mutableStateOf("") }
    var eventEndTime by remember { mutableStateOf("") }
    var event by remember { mutableStateOf<Event?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showAddEventDialog by remember { mutableStateOf(false) } // Estado para el Dialog
    val context = LocalContext.current
    val eventRepository = EventRepository() // Repositorio de eventos
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = eventName,
            onValueChange = { eventName = it },
            label = { Text("Nombre del evento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = descriptionName,
            onValueChange = { descriptionName = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        Row {
            TextField(
                value = eventStartDate,
                onValueChange = { eventStartDate = it },
                label = { Text("Fecha inicio (yyyy-MM-dd)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            IconButton(onClick = { showDatePicker(context) { eventStartDate = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            TextField(
                value = eventEndDate,
                onValueChange = { eventEndDate = it },
                label = { Text("Fecha fin (yyyy-MM-dd)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            IconButton(onClick = { showDatePicker(context) { eventEndDate = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = eventStartTime,
                onValueChange = { eventStartTime = it },
                label = { Text("Hora inicio (HH:mm)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            IconButton(onClick = { showTimePicker(context) { eventStartTime = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar hora")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Row {
            TextField(
                value = eventEndTime,
                onValueChange = { eventEndTime = it },
                label = { Text("Hora fin (HH:mm)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            IconButton(onClick = { showTimePicker(context) { eventEndTime = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar hora")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

Button(
    onClick = {
        errorMessage = null
        if (eventName.isEmpty() || !isValidDate(eventStartDate) || eventStartTime.isEmpty()) {
            errorMessage = "Por favor complete todos los campos correctamente."
        } else {
            val startDateTime = try {
                java.time.Instant.parse("${eventStartDate}T${eventStartTime}:00Z")
            } catch (e: Exception) { null }
            val endDateTime = if (eventEndDate.isNotEmpty() && eventEndTime.isNotEmpty()) {
                try {
                    java.time.Instant.parse("${eventEndDate}T${eventEndTime}:00Z")
                } catch (e: Exception) { null }
            } else null

            if (startDateTime != null) {
                event = Event(
                    id_trip = UserSession.idTrip ?: return@Button,
                    id_event = null,
                    title = eventName,
                    description = descriptionName,
                    start_datetime = kotlinx.datetime.Instant.fromEpochMilliseconds(
                        startDateTime.toEpochMilli()
                    ),
                    end_datetime = endDateTime?.let {
                        kotlinx.datetime.Instant.fromEpochMilliseconds(it.toEpochMilli())
                    },
                    created_by = UserSession.userName
                )
            }
            coroutineScope.launch {
                try {
                    eventRepository.addEvent(event!!)
                    Toast.makeText(context, "Evento creado correctamente", Toast.LENGTH_SHORT).show()
                    onDismiss() // Cierra el diálogo
                } catch (e: Exception) {
                    errorMessage = "Error al guardar el evento: ${e.message}"
                }
            }
        }
    }
) {
    Text("Guardar Evento")
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
@Preview(showBackground = true)
@Composable
fun PreviewAddEventScreen() {
    AddEventScreen( onDismiss = {})
}