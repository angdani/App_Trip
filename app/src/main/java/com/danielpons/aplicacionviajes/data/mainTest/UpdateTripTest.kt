package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.apptrip.model.Trip

suspend fun main() {
    val tripRepository = TripRepository()
    val tripId = 21// ID del viaje que deseas actualizar

    val trip = Trip(
        id_trip = tripId,
        name = "Viaje actualizado",
        startDate = "2023-10-01",
        endDate = "2023-10-15",
    )

    trip.startDate?.let {
        trip.endDate?.let { it1 ->
            tripRepository.updateTrip(
            id = tripId,
            name = trip.name,
            startDate = it,
            endDate = it1
        )
        }
    }


}