package com.danielpons.aplicacionviajes.data.mainTest

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.test.core.app.ApplicationProvider
import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.User
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import kotlinx.coroutines.runBlocking


suspend fun main() {
    val userRepository = UserRepository()
    val supabaseConnection = SupabaseConnection()
    val listUsers = userRepository.getUsers()
    val tripRepository = TripRepository()
    val listTrips = tripRepository.getAllTrips()
    val user : User?
    val email = "usuario1@example.com"
    val username = "Ana Perez"
    val isUserRegistered : Boolean

    /*
    user = userRepository.getUserRegistered(email, username)

    if (user != null) {
        println("Usuario encontrado: ${user.username}, ID: ${user.id_user}")
    } else {
        println("Usuario no encontrado.")
    }
    */



    println("Lista de usuarios: ")
    for (user in listUsers) {
        println("ID: ${user.id_user}, Username: ${user.username}, Email: ${user.email}")
    }


    println("Lista de viajes: ")
    if(listTrips.isEmpty()) {
        println("No hay viajes disponibles.")
    }else{
        for (trip in listTrips) {
            println("ID: ${trip.id_trip}, Nombre: ${trip.name}, Descripción: ${trip.description}")
        }
    }





}