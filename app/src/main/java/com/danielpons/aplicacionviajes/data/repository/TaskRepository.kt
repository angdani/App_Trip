package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.Task
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class TaskRepository {
    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase


    suspend fun getTasksByTripId(idTrip: Int): List<Task> {
        val response = supabase.from(
            "tasks"
        ).select {
            filter {
                eq("id_trip", idTrip)
            }
        }.decodeList<Task>()


        return try {
            response
        } catch (e: Exception) {
            println("Error al decodificar la respuesta: ${e.message}")
            emptyList()
        }

    }

    suspend fun addTask(task: Task) {
        supabase.from("tasks").insert(task)
    }

    suspend fun updateTask(task: Task) {
        val rpcParams = buildJsonObject {
            put("p_id_task", task.id_task ?: 0) // Asegúrate de que id_task no sea nulo
            put("p_title", task.title)
            put("p_due_date", task.due_date)
        }

        try {
            supabase.postgrest.rpc("update_task", rpcParams)

        } catch (e: Exception) {
            println("Error al actualizar la tarea: ${e.message}")
        }

    }

    suspend fun updateTaskCompleted(task: Task) {
        val rpcParams = buildJsonObject {
            put("p_id_task", task.id_task ?: 0) // Asegúrate de que id_task no sea nulo
            put("p_is_completed", task.is_completed)
        }

        try {
            supabase.postgrest.rpc("update_task_completed", rpcParams)

        } catch (e: Exception) {
            println("Error al actualizar la tarea: ${e.message}")
        }

    }

    suspend fun deleteTask(taskId: Int): Boolean {
        return try {
            supabase.from("tasks").delete {
                filter {
                    eq("id_task", taskId)
                }
            }
            true
        } catch (e: Exception) {
            println("Error al eliminar la tarea: ${e.message}")
            false
        }
    }
}