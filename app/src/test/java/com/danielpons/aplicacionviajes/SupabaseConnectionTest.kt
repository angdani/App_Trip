package com.danielpons.aplicacionviajes

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class SupabaseConnectionTest {

    // Test para verificar la conexión a Supabase
    @Test
    fun testSupabaseConnection() {
        val supabaseConnection = SupabaseConnection()
        val client = supabaseConnection.supabase

        // Verifica que el cliente no sea nulo
        assertNotNull("La conexión a Supabase no se ha establecido correctamente", client)
        println("Conexión a Supabase establecida correctamente: $client")
    }
}