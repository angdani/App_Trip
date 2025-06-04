package com.danielpons.aplicacionviajes.data.mainTest
import com.danielpons.aplicacionviajes.data.model.Event
import com.danielpons.aplicacionviajes.data.repository.EventRepository

suspend fun main() {
    val event = Event(
        id_trip = 21,
        title = "Test Event",
        description = "This is a test event",
        start_datetime = kotlinx.datetime.Instant.parse("2023-10-01T10:00:00Z"),
        end_datetime = kotlinx.datetime.Instant.parse("2023-10-01T12:00:00Z"),
        created_by = "admin"
    )


    val eventRepository = EventRepository()
    eventRepository.addEvent(event)
    println(event)

    event.title = "Updated Test Event"
    event.description = "This is an updated test event"
    eventRepository.updateEvent(event)
    println(event)
}