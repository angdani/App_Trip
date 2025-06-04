package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("id_event")
    val id_event: Int? = null,
    val id_trip: Int,
    var title: String,
    var description: String? = null,
    val start_datetime: Instant,
    val end_datetime: Instant? = null,
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val category: String? = null,
    val reminder_enabled: Boolean = false,
    val reminder_time: Instant? = null,
    val cost: Double? = null,
    val cost_currency: String = "USD",
    val created_by: String? = null,
    val created_at: Instant? = null,
    val updated_at: Instant? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val is_deleted: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)