package com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Event
import com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent.EventItem
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import com.danielpons.aplicacionviajes.data.repository.EventRepository
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.AddEventScreen
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.EditEventScreen
import com.danielpons.aplicacionviajes.ui.theme.Components.DropDown.DropDownMenuDeleteEdit
import kotlinx.coroutines.coroutineScope


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventsSection(events: List<Event>) {
    var showAddEvent by remember { mutableStateOf(false) }
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showEditEventDialog by remember { mutableStateOf(false) }
    val eventRepository = EventRepository()



    // Dialog para mostrar AddEventScreen
    if (showAddEvent) {
        Dialog(onDismissRequest = { showAddEvent = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                AddEventScreen(
                    onDismiss = { showAddEvent = false }
                )
            }
        }
    }

    // Dialog para mostrar EditEventScreen
    if (showEditEventDialog) {
        Dialog(onDismissRequest = { showEditEventDialog = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                EditEventScreen(
                    onDismiss = { showAddEvent = false
                    },
                    event = selectedEvent // Pasamos el evento seleccionado
                )
            }
        }
    }

    Box {
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(events) { event ->
                            var eventExpanded by remember { mutableStateOf(false) }

                            Box(
                                modifier = Modifier.combinedClickable(
                                    onClick = {
                                    eventExpanded = true
                                }
                                )

                            ) {
                                Card(
                                    modifier = Modifier
                                        .size(350.dp, 150.dp), // Ajusta el tamaño de la Card
                                    shape = MaterialTheme.shapes.medium,
                                    elevation = CardDefaults.cardElevation(8.dp),
                                    border = BorderStroke(1.dp, Color.Gray),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                ) {
                                    EventItem(event = event)
                                }

                                DropDownMenuDeleteEdit(
                                    expanded = eventExpanded,
                                    onDismissRequest = { eventExpanded = false },
                                    onEditTrip = {
                                        selectedEvent = event
                                        showEditEventDialog = true
                                        eventExpanded = false
                                    },
                                    onDeleteTrip = {
                                        // Lógica para eliminar el evento
                                        coroutineScope {
                                            event.id_event?.let { eventRepository.deleteEventById(it) }
                                        }
                                        eventExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { showAddEvent = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .size(150.dp, 40.dp)
        ) {
            Text(text = "Añadir evento")
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewEventsSection() {
    val sampleEvents = listOf(
        Event(
            id_event = 1,
            id_trip = 1,
            title = "Excursión al lago",
            description = "Caminata y picnic junto al lago.",
            start_datetime = kotlinx.datetime.Instant.parse("2023-11-10T09:00:00Z"),
            end_datetime = kotlinx.datetime.Instant.parse("2023-11-10T12:00:00Z"),
            location = "Lago Azul",
            created_by = "Juan Pérez",
        ),
        Event(
            id_event = 2,
            id_trip = 1,
            title = "Cena grupal",
            description = "Cena en restaurante local.",
            start_datetime = kotlinx.datetime.Instant.parse("2023-11-10T20:00:00Z"),
            end_datetime = null,
            location = "Restaurante El Buen Sabor",
            created_by = "Juan Pérez",
        )
    )
    EventsSection(events = sampleEvents)
}