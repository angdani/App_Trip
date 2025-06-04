package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
@Serializable
data class User(
    val id_user: String? = null,
    val username: String,
    val email: String? = null,
    val password: String? = null,
    val created_at: Instant? = null,
    val lastLogin: Instant? = null,
) {
    companion object{
        fun createBasic(username: String, email: String, password: String): User {
            return User(
                username = username,
                email = email,
                password = password
            )
        }
    }
}