package com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Event
import kotlinx.datetime.Clock
import kotlinx.datetime.plus

@Composable
fun EventItem(event: Event, onClick: (Event) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(event) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.bodyLarge
            )

            event.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Text(
                text = "Inicio: ${event.start_datetime}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )

            event.end_datetime?.let {
                Text(
                    text = "Fin: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            event.location?.let {
                Text(
                    text = "Ubicación: $it",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            event.cost?.let {
                Text(
                    text = "Costo: $it ${event.cost_currency}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewEventItem() {
    EventItem(
        event = Event(
            id_event = 1,
            id_trip = 101,
            title = "Evento de prueba",
            description = "Descripción del evento de prueba",
            start_datetime = Clock.System.now(),
            end_datetime = Clock.System.now().plus(3600, kotlinx.datetime.DateTimeUnit.SECOND),
            location = "Ubicación de prueba",
            cost = 50.0,
            cost_currency = "USD"
        ),
        onClick = {}
    )
}