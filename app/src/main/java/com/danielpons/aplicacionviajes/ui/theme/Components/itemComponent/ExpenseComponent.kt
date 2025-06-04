package com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Expense

@Composable
fun ExpenseItem(expense: Expense) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = " ${expense.description ?: "Sin descripción"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Gastó: $${expense.amount}", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewExpenseItemSimple() {
    ExpenseItem(
        expense = Expense(
            id_trip = 202,
            amount = 150.75,
            description = "Cena en restaurante",
            id_expense = TODO(),
            category = TODO(),
            payment_method = TODO(),
            receipt_image_url = TODO(),
            created_by = TODO(),
            created_at = TODO(),
            is_dirty = TODO(),
            is_deleted = TODO(),
            last_modified = TODO(),
            server_id = TODO()
        )
    )
}