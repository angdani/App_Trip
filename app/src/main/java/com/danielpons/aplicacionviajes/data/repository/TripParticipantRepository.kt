package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.TripParticipant
import io.github.jan.supabase.postgrest.from

class TripParticipantRepository {

    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase

    suspend fun deleteParticipant(taskId: Int): Boolean {
        return try {
            supabase.from("trip_participant").delete {
                filter {
                    eq("id", taskId)
                }
            }
            true
        } catch (e: Exception) {
            println("Error al eliminar la notificacion: ${e.message}")
            false
        }
    }

    suspend fun addParticipant(tripParticipant: TripParticipant) {
        try{
            println("Intentando añadir participante: ${tripParticipant.id_user} al viaje: ${tripParticipant.id_trip}")
            supabase.from("trip_participants").insert(tripParticipant)
        } catch (e: Exception) {
            println("Error al añadir participante: ${e.message}")
        }
    }

}