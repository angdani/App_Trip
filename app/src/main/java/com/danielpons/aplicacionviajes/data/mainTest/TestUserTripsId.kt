package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.repository.TripRepository
import java.util.UUID

 suspend fun main() {
    val userId: String =
        ("20250514172213_9f90") // Reemplaza con el UUID real del usuario // Reemplaza con el ID de usuario que deseas probar
    val tripRepository = TripRepository()
    val trips = tripRepository.getTripsByUserId(userId)

    if (trips.isNotEmpty()) {
        println("Viajes encontrados para el usuario $userId:")
        for (trip in trips) {
            println("ID: ${trip.name}, Destino: ${trip.name}")
            println("Fecha de inicio: ${trip.startDate}, Fecha de fin: ${trip.endDate}")
        }
    } else {
        println("No se encontraron viajes para el usuario $userId.")
    }
}