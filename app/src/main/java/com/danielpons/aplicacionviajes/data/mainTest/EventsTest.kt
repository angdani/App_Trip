package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.repository.EventRepository

suspend fun main() {
    val eventRepository = EventRepository()
    val tripId = 22 // Cambia esto por el ID del viaje que deseas usar para la prueba
    val events = eventRepository.getTripsByUserId(tripId)


    if (events.isNotEmpty()) {
        println("Viajes encontrados para el usuario $tripId:")
        for (event in events) {
            println("ID: ${event.title}, Destino: ${event.description}")
            println("Fecha de inicio: ${event.start_datetime}, Fecha de fin: ${event.start_datetime}")
        }
    } else {
        println("No se encontraron viajes para el usuario $tripId.")
    }
}