package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.User
import com.danielpons.apptrip.model.Trip
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.util.UUID


class TripRepository {

    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase

    // Ejemplo de método para obtener todos los viajes
    suspend fun getAllTrips(): List<Trip> {
        return supabaseConnection.supabase
            .from("trips")
            .select()
            .decodeList<Trip>()
    }

  suspend fun addTrip(trip: Trip): Int  {
      val rpcParams = buildJsonObject {
          put("p_name", trip.name)
          put("p_start_date", trip.startDate)
          put("p_end_date", trip.endDate)
          put("p_created_by", UserSession.userId)
      }
     return supabase.postgrest.rpc("create_trip", rpcParams)
          .decodeAs<Int>() ?: throw Exception("Error al crear el viaje")
  }

    suspend fun getTripsByUserId(userId: String): List<Trip> {
        val response = supabase.postgrest.rpc(
            "get_user_trips",
            mapOf("id_user" to userId) // Asegúrate de que el nombre coincida con el esperado en la función RPC
        )
        return try {
            response.decodeList<Trip>()
        } catch (e: Exception) {
            // Maneja el caso en que la respuesta no sea una lista de viajes
            emptyList()
        }
    }


}