package com.danielpons.aplicacionviajes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Int? = null,
    @SerialName("id_trip")
    val idTrip : Int? = null,
    @SerialName("id_user")
    val idUser : String? = null,
    val title : String,
    val message: String,
    @SerialName("create_at")
    val createAt: String? = null,
    val isRead: Boolean = false,
    val sender : String? = null,
    val receiver: String? = null
)
