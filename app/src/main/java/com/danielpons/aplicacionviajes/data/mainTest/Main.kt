package com.danielpons.aplicacionviajes.data.mainTest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.User
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import kotlinx.coroutines.runBlocking


suspend fun main() {
    val userRepository = UserRepository()
    val supabaseConnection = SupabaseConnection()
    val listUsers = userRepository.getUsers()

    println("Lista de usuarios: ")
    for (user in listUsers) {
        println("ID: ${user.id_user}, Username: ${user.username}, Email: ${user.email}")
    }


}