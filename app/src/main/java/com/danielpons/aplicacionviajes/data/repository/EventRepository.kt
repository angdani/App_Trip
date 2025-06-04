package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Event
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class EventRepository {

    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase


    suspend fun getTripsByUserId(tipId: Int): List<Event> {
        val response = supabase.postgrest.rpc(
            "get_trip_events",
            mapOf("p_trip_id" to tipId) // Asegúrate de que el nombre coincida con el esperado en la función RPC
        )

        println("Respuesta cruda del servidor: ${response.toString()}")
        // Imprime la respuesta cruda para depuración
        val decodedResponse = response.decodeList<Event>()
        println("Respuesta decodificada: $decodedResponse")
        return try {
            response.decodeList<Event>()
        } catch (e: Exception) {
            println("Error al decodificar la respuesta: ${e.message}")
            emptyList()
        }
    }

    suspend fun addEvent(event: Event) {
        val rpcParams = buildJsonObject {
            put("p_id_trip", event.id_trip)
            put("p_title", event.title)
            put("p_description", event.description)
            put("p_start_datetime", event.start_datetime.toString())
            put("p_end_datetime", event.end_datetime?.toString())
            put("p_created_by", UserSession.userName ?: "default_user") // Reemplaza con el ID del usuario actual
        }
        supabase.postgrest.rpc("insert_event", rpcParams)
    }

    suspend fun updateEvent(event: Event): Boolean {
        val rpcParams = buildJsonObject {
            put("p_id", 20)
            put("p_title", event.title)
            put("p_description", event.description)
            put("p_start_datetime", event.start_datetime.toString())
            put("p_end_datetime", event.end_datetime?.toString())
            put("p_created_by", event.created_by)
        }

        return try {
            supabase.postgrest.rpc("update_event", rpcParams)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteEventById(eventId: Int) {
        supabase
            .from("events")
            .delete {
                filter {
                    eq("id_event", eventId)
                }
            }
    }


}