package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.repository.UserRepository

suspend fun main() {
    val userRepository = UserRepository()

    val user = userRepository.getUserByUsername("admin")

    println("User found: ${user?.username ?: "No user found"}")
    if (user != null) {
        println("User details: ${user.username}, ${user.email}")
        print ("User password: ")
        println("Created at: ${user.created_at}")
    } else {
        println("No user found with the given username.")
    }


}
