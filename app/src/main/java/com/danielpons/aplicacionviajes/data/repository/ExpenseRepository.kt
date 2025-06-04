package com.danielpons.aplicacionviajes.data.repository

import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.model.Expense
import com.danielpons.aplicacionviajes.data.model.Notification
import io.github.jan.supabase.postgrest.from

class ExpenseRepository {

    private val supabaseConnection = SupabaseConnection()
    private val supabase = supabaseConnection.supabase


    suspend fun deleteExpense(expenseId: Int): Boolean {
        return try {
            supabase.from("exepenses").delete {
                filter {
                    eq("id_expense", expenseId)
                }
            }
            true
        } catch (e: Exception) {
            println("Error al eliminar la notificacion: ${e.message}")
            false
        }
    }

    suspend fun addExpense(expense : Expense) {
        supabase.from("expenses").insert(expense)
    }

    suspend fun getExpensesByTripId(tripId : Int): List<Expense> {
        return supabase.from("expenses")
            .select {
                filter {
                    eq("id_trip", tripId)
                }
            }
            .decodeList<Expense>()
    }
}