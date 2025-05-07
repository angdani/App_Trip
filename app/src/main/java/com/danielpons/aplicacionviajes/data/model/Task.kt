package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val id_task: Int? = null,
    val id_trip: Int,
    val title: String,
    val description: String? = null,
    val due_date: String? = null, // Using String for simplicity
    val is_completed: Boolean = false,
    val priority: Int = 2,
    val assigned_to: String? = null,
    val created_by: String? = null,
    val created_at: Instant? = null,
    val completed_at: Instant? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)