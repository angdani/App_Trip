package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.travelapp.model.TripDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc

class TripRepository {
    private  var supabase : SupabaseClient = SupabaseConnection().supabase

    suspend fun addTrip(trip: TripDto) {
        try {
            if (trip.startDate != null && trip.endDate != null) {
                supabase.postgrest.rpc("addViaje", mapOf("p_name" to trip.name, "p_start_date" to trip.startDate, "p_end_date" to trip.endDate))
            } else {
                supabase.postgrest.rpc("addViaje", mapOf("p_name" to trip.name))
            }
       } catch (e: Exception) {
            // Captura cualquier otra excepción general
            println("An error occurred: ${e.message}")
            e.printStackTrace()
        } finally {
            supabase.close()
        }
    }

    suspend fun selectUserTrips(): List<TripDto> {
            val listTrip : List<TripDto> = supabase.postgrest["Viajes"].select().decodeList()
        return listTrip
        }

    }

