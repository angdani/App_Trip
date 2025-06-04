package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.Notification
import io.github.jan.supabase.postgrest.from

class NotificationRepository {
    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase

    suspend fun deleteNotification(taskId: Int): Boolean {
        return try {
            supabase.from("notifications").delete {
                filter {
                    eq("id", taskId)
                }
            }
            true
        } catch (e: Exception) {
            println("Error al eliminar la notificacion: ${e.message}")
            false
        }
    }

    suspend fun addNotification(notification: Notification) {
        supabase.from("notifications").insert(notification)
    }

    suspend fun getNotificationsByUserName(userName: String): List<Notification> {
        return supabase.from("notifications")
            .select {
                filter {
                    eq("receiver", userName)
                }
            }
            .decodeList<Notification>()
    }

}