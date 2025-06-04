package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TripParticipant(
    val id_participant: Int? = null,
    val id_trip: Int,
    val id_user: String?,
    val role: String = "OWNER",
    val joined_at: Instant? = null,
    val invitation_status: String = "ACCEPTED",
    // Sync fields
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
){
    enum class ParticipantRole {
        OWNER, EDITOR, VIEWER
    }
    enum class InvitationStatus {
        ACCEPTED, PENDING, REJECTED
    }
}

