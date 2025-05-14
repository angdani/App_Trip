package com.danielpons.aplicacionviajes

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SupabaseUserTest {
    private val userRepository = UserRepository()
    private val supabaseConnection = SupabaseConnection()

    @Test
    fun testRegisterUser(): Unit = runBlocking {
        val email = "ejemplo@ejemplo"
        val password = "1234"
        val name = "Daniel"
        userRepository.registerUser(
            email = email,
            password = password,
            username = name
        )

    }

    @Test
    fun testGetUserByEmail(): Unit = runBlocking {
        val user = userRepository.getUserByUsername("ejemplo@ejemplo")
        println("Usuario obtenido:")
        if (user != null) {
            println(user.username)
        }


    }
}