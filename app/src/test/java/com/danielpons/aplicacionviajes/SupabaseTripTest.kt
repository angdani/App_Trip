package com.danielpons.aplicacionviajes

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import io.github.jan.supabase.postgrest.postgrest
import org.junit.Test

class SupabaseTripTest {
    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase.postgrest
    private val tableName = "trips"
    @Test
    fun getTrips() {

    }
}