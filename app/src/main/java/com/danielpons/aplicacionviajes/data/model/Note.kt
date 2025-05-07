package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id_note: Int? = null,
    val id_trip: Int,
    val title: String,
    val content: String,
    val created_by: String? = null,
    val created_at: Instant? = null,
    val updated_at: Instant? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)