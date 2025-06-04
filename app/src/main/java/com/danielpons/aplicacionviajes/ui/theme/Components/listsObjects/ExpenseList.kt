package com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.danielpons.aplicacionviajes.data.model.Expense
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.AddExpenseScreen
import com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent.ExpenseItem

@Composable
fun ExpenseListScreen(
    initialExpenses: List<Expense>,
) {
    var expenses by remember { mutableStateOf(initialExpenses) }
    var showAddExpenseDialog by remember { mutableStateOf(false) }

    // Dialog para agregar gasto
    if (showAddExpenseDialog) {
        Dialog(onDismissRequest = { showAddExpenseDialog = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth()
            ) {
                AddExpenseScreen(onAddExpense = { newExpense ->
                    expenses = expenses + newExpense
                    showAddExpenseDialog = false
                })
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .fillMaxSize(),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Lista de gastos:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            if (expenses.isEmpty()) {
                Text(
                    text = "No hay gastos registrados.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                ) {
                    items(expenses.size) { index ->
                        ExpenseItem(expense = expenses[index])
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Total: ${expenses.sumOf { it.amount }}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = { showAddExpenseDialog = true }
                ) {
                    Text(
                        text = "Agregar gasto",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewExpenseListScreen() {
    ExpenseListScreen(
        initialExpenses = listOf(
            Expense(
                id_trip = 101,
                amount = 50.0,
                description = "Taxi al aeropuerto",
                category = "Transporte"
            ),
            Expense(
                id_trip = 102,
                amount = 30.0,
                description = "Cena en restaurante",
                category = "Comida"
            ),
            Expense(
                id_trip = 103,
                amount = 100.0,
                description = "Hotel por noche",
                category = "Alojamiento"
            )
        ),
    )
}