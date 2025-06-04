package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Budget(
    val id_budget: Int? = null,
    val category: String,
    val planned_amount: Double,
    val actual_amount: Double = 0.0,
    val currency: String = "USD",
    val notes: String? = null,
    val created_by: String? = null,
    val created_at: Instant? = null,
    val updated_at: Instant? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)
