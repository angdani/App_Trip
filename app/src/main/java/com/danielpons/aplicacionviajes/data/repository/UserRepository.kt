package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.User
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put

class UserRepository {
    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase

    suspend fun registerUser(name: String, email: String, password: String) {
        try {
            val userSupabase = User.createBasic(
                username = name,
                email = email,
                password = password
            )

            println("Intentando registrar usuario: ${userSupabase.username}")

            val rpcParams = buildJsonObject {
                put("p_username", userSupabase.username)
                put("p_email", userSupabase.email)
                put("p_password", userSupabase.password)
            }
            supabase.postgrest.rpc("insert_user", rpcParams)



            println("Usuario registrado correctamente: ${userSupabase.username}")
        } catch (e: Exception) {
            println("Error al registrar usuario: ${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun getUsers(): List<User> {
        val listUsers = supabase.from(
            "users"
        ).select().decodeList<User>()

        return listUsers
    }

}
