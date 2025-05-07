package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Expense(
    val id_expense: Int? = null,
    val id_budget: Int? = null,
    val id_trip: Int,
    val amount: Double,
    val description: String? = null,
    val date: String, // Using String for simplicity
    val category: String? = null,
    val payment_method: String? = null,
    val receipt_image_url: String? = null,
    val created_by: String? = null,
    val created_at: Instant? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val is_deleted: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)