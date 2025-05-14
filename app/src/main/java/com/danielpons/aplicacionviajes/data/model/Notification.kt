package com.danielpons.aplicacionviajes.data.model

data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val date: String,
    val isRead: Boolean = false
)
