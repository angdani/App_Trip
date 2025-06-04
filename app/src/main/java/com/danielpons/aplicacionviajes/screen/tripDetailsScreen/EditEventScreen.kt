package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import android.widget.Toast
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
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Event
import com.danielpons.aplicacionviajes.data.repository.EventRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun EditEventScreen(onDismiss: () -> Unit, event: Event? = null) {
    var eventName by remember { mutableStateOf(event?.title ?: "") }
    var descriptionName by remember { mutableStateOf(event?.description ?: "") }
    var eventStartDate by remember { mutableStateOf(event?.start_datetime?.toLocalDateTime(TimeZone.currentSystemDefault())?.date.toString() ?: "") }
    var eventStartTime by remember { mutableStateOf(event?.start_datetime?.toLocalDateTime(TimeZone.currentSystemDefault())?.time.toString() ?: "") }
    var eventEndDate by remember { mutableStateOf(event?.end_datetime?.toLocalDateTime(TimeZone.currentSystemDefault())?.date.toString() ?: "") }
    var eventEndTime by remember { mutableStateOf(event?.end_datetime?.toLocalDateTime(TimeZone.currentSystemDefault())?.time.toString() ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val eventRepository = EventRepository()
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
                        val updatedEvent = Event(
                            id_trip = event?.id_trip ?: UserSession.idTrip ?: return@Button,
                            id_event = event?.id_event,
                            title = eventName,
                            description = descriptionName,
                            start_datetime = kotlinx.datetime.Instant.fromEpochMilliseconds(
                                startDateTime.toEpochMilli()
                            ),
                            end_datetime = endDateTime?.let {
                                kotlinx.datetime.Instant.fromEpochMilliseconds(it.toEpochMilli())
                            },
                            created_by = event?.created_by ?: UserSession.userName
                        )
                        coroutineScope.launch {
                            try {
                                eventRepository.updateEvent(updatedEvent)
                                Toast.makeText(context, "Evento actualizado correctamente", Toast.LENGTH_SHORT).show()
                                onDismiss()
                            } catch (e: Exception) {
                                errorMessage = "Error al actualizar el evento: ${e.message}"
                            }
                        }
                    }
                }
            }
        ) {
            Text("Editar Evento")
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