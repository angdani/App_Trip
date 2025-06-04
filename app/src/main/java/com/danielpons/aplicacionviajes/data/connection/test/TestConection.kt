package com.danielpons.aplicacionviajes.data.connection.test


import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val supabaseConnection = SupabaseConnection()

    try {
        // Ejemplo: Obtener datos de una tabla llamada "trips"
        val response = supabaseConnection.supabase
        println("Conexión exitosa. Datos obtenidos: $response")
    } catch (e: Exception) {
        println("Error al conectar con Supabase: ${e.message}")
    }
}