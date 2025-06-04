package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.repository.TripRepository

suspend fun main() {
    val tripId = 22 // ID del viaje que deseas eliminar
    val tripRepository = TripRepository()

    try {
        tripRepository.deleteTripById(tripId)
        println("Viaje eliminado con éxito.")
    } catch (e: Exception) {
        println("Error al eliminar el viaje: ${e.message}")

    }

}