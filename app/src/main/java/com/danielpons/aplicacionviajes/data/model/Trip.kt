package com.danielpons.apptrip.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val id_trip: Int?,                // Puede ser null al crearlo, porque lo genera el backend
    val name: String,
    val startDate: String?,      // Usamos String para simplificar la conversión de fechas con Retrofit
    val endDate: String?,
    val description: String? = null,
    val cover_image_url: String? = null,
    val status: TripStatus = TripStatus.PLANNED,
    val created_by: Long? = null,
    val created_at: Instant? = null,
    val updated_at: Instant? = null,
    val is_public: Boolean = false,
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
) {
    enum class TripStatus {
        PLANNED, IN_PROGRESS, COMPLETED, CANCELLED
    }
}

