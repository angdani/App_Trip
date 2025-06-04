package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.model.TripParticipant
import com.danielpons.aplicacionviajes.data.repository.TripParticipantRepository

suspend fun main() {
    val tripParticipantRepository = TripParticipantRepository()

    val participant = TripParticipant(
        id_trip = 21,
        id_user = "20250514185550_f1c7",
        role = "editor",
        invitation_status = "accepted"
    )
    println("Adding participant: $participant")
    tripParticipantRepository.addParticipant(participant)
    println("Participant added successfully.")
}