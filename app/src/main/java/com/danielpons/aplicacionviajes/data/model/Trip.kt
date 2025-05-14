package com.danielpons.apptrip.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val id_trip: Int?,
    val name: String,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")// Valor predeterminado
    val endDate: String? = null,    // Valor predeterminado
    val description: String? = null,
    val cover_image_url: String? = null,
    val status: TripStatus = TripStatus.PLANNED,
    val created_by: String? = null,
    val created_at: Instant? = null,
    val updated_at: Instant? = null,
    val is_public: Boolean = false,
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
) {
    @Serializable
    enum class TripStatus {
        @SerialName("planned")
        PLANNED,
        @SerialName("in_progress")
        IN_PROGRESS,
        @SerialName("completed")
        COMPLETED,
        @SerialName("cancelled")
        CANCELLED
    }
}

