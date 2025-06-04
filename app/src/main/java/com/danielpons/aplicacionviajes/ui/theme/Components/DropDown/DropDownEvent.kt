package com.danielpons.aplicacionviajes.ui.theme.Components.DropDown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Event
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun EventDropDownMenu(
    event: Event,
    onEditEvent: (Event) -> Unit,
    onDeleteEvent: (Event) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable { expanded = true }, // Muestra el menú al hacer clic
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = event.title, style = MaterialTheme.typography.bodyLarge)
                event.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Editar") },
                onClick = {
                    expanded = false
                    onEditEvent(event) // Lógica para editar el evento
                }
            )
            DropdownMenuItem(
                text = { Text("Eliminar") },
                onClick = {
                    expanded = false
                    onDeleteEvent(event) // Lógica para eliminar el evento
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventDropDownMenu() {
    EventDropDownMenu(
        event = Event(
            id_event = 1,
            id_trip = 1,
            title = "Evento de prueba",
            description = "Descripción del evento de prueba",
            start_datetime = kotlinx.datetime.Instant.parse("2023-11-10T09:00:00Z"),
            end_datetime = null,
            location = "Lugar de prueba",
            created_by = "Usuario de prueba"
        ),
        onEditEvent = { /* Acción de editar */ },
        onDeleteEvent = { /* Acción de eliminar */ }
    )
}