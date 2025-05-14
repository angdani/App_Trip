package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.repository.UserRepository

suspend fun main() {
    val userRepository = UserRepository()
    val password = "1234"
    val username = "prueba"
    val user = userRepository.getUserRegistered(username, password)

    print(user)
}