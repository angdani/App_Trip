package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PointOfInterest(
    val id_poi: Int? = null,
    val id_trip: Int,
    val name: String,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val notes: String? = null,
    val visit_date: String? = null, // Using String for simplicity
    val category: String? = null,
    val is_visited: Boolean = false,
    val added_by: String? = null,
    val created_at: Instant? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)