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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.util.Locale
import java.time.format.DateTimeFormatter
val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'a las' HH:mm", Locale("es", "ES"))

@Composable
fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
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

            val startDateTime =
                event.start_datetime.toLocalDateTime(TimeZone.currentSystemDefault())
            Text(
                text = "Inicio: ${startDateTime.toJavaLocalDateTime().format(formatter)}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )

            event.end_datetime?.let {
                val endDateTime = it.toLocalDateTime(TimeZone.currentSystemDefault())
                Text(
                    text = "Fin: ${endDateTime.toJavaLocalDateTime().format(formatter)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            event.created_by?.let {
                Text(
                    text = "Creador: $it",
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
            location = "Zaragoza",
            created_by = "Usuario de prueba",
        )
    )
}