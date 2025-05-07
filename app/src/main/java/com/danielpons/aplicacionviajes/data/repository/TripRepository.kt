package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.apptrip.model.Trip
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class TripRepository (    private val supabaseConnection: SupabaseConnection
) {
    // Aquí puedes agregar métodos para interactuar con la base de datos
    // Por ejemplo, obtener viajes, agregar un viaje, etc.
    // Puedes usar el cliente de Supabase para hacer consultas a la base de datos

    // Ejemplo de método para obtener todos los viajes
    suspend fun getAllTrips(): List<Trip> {
        return supabaseConnection.supabase
            .from("trips")
            .select()
            .decodeList<Trip>()
    }

    // Método para agregar un nuevo viaje
    suspend fun addTrip(trip:
                        Trip): Trip = withContext(Dispatchers.IO) {
        supabaseConnection.supabase
            .from("trips")
            .insert(trip)
            .decodeSingle<Trip>()
    }




}