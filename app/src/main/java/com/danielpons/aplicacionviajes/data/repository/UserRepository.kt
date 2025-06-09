package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.User
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class UserRepository {
    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase

    suspend fun registerUser(username: String, email: String, password: String) {
        try {
            val userSupabase = User.createBasic(
                username = username,
                email = email,
                password = password
            )

            println("Intentando registrar usuario: ${userSupabase.username}")

            val rpcParams = buildJsonObject {
                put("p_username", userSupabase.username)
                put("p_email", userSupabase.email)
                put("p_password", userSupabase.password)
            }
            supabase.postgrest.rpc("insert_user", rpcParams)



            println("Usuario registrado correctamente: ${userSupabase.username}")
        } catch (e: Exception) {
            println("Error al registrar usuario: ${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun getUsers(): List<User> {
        val listUsers = supabase.from(
            "users"
        ).select().decodeList<User>()

        return listUsers
    }

    suspend fun getUsersNames(): List<String> {
        val listUsernames = supabase.from(
            "users"
        ).select(
            columns = Columns.list(
                "username"
            )
        ).decodeList<User>()

        return listUsernames.map { it.username }
    }

    suspend fun getIdUser(username: String): String {
        val idUser = supabase.from(
            "users"
        ).select(

            columns = Columns.list(
                "id_user"
            )
        ){
            filter { eq("username", username ) }
        }.decodeSingleOrNull<String>()

        return idUser ?: throw Exception("User not found")
    }

    suspend fun getUserByUsername(username: String): User? {
    val users = supabase.from("users")
        .select(){
            filter { eq("username", username) }
        }
        .decodeSingleOrNull<User>()
    return users
}




  suspend fun getUserRegistered(username: String, password: String): User? {
      val rpcParams = buildJsonObject {
          put("p_username", username)
          put("p_password", password)
      }
      val user = supabase.postgrest.rpc("get_user_by_credentials", rpcParams)
          .decodeSingleOrNull<User>()
      return user
  }

    suspend fun deleteUser(username: String, idUser : String) {
        supabase.from("trip_participants")
            .delete{
                filter { eq("id_user", idUser) }
            }
       supabase.from("users")
            .delete{
                filter { eq("username", username) }
            }
            }
    }






