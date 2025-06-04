package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.apptrip.model.Trip
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.rpc
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


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

    suspend fun addTrip(trip: Trip): Int {
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

 suspend fun getTripNameById(tripId: Int): String? {
     val result = supabase
         .from("trips")
         .select(
             columns = Columns.list(
                 "name" // Selecciona solo el campo "name"
             )
         ) {
             filter {
                 eq("id_trip", tripId)
             }
         }
         .decodeSingle<Map<String, String>>() // Decodifica a un mapa

     return result?.get("name") // Obtiene el valor del campo "name"
 }

    suspend fun getTripById(tripId: Int): Trip {
        val result = supabase
            .from("trips")
            .select()
             {
                filter {
                    eq("id_trip", tripId)
                }
            }
            .decodeSingle<Trip>() // Decodifica a un mapa

        return result // Obtiene el valor del campo "name"
    }

    suspend fun deleteTripById(tripId: Int) {
        supabase
            .from("trips")
            .delete {
                filter {
                    eq("id_trip", tripId)
                }
            }
    }

  suspend fun updateTrip(id: Int, name: String, startDate: String, endDate: String) {
      supabase.postgrest.rpc(
          "update_trip_details",
          buildJsonObject {
              put("p_id_trip", id)
              put("p_name", name)
              put("p_start_date", startDate)
              put("p_end_date", endDate)
          }
      )
  }

}
