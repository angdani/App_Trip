package com.danielpons.aplicacionviajes.data.connection

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseConnection {

    val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://shkhaigvghcylvwfngdp.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNoa2hhaWd2Z2hjeWx2d2ZuZ2RwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDA0MTMyMTcsImV4cCI6MjA1NTk4OTIxN30.1zb7j3n3IAFH2BzsX2nJV6b0wCgtGx14g1JO8lm-_3k"
    ) {
        install(Postgrest)
    }

}