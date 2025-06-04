package com.danielpons.aplicacionviajes.screen.tripDetailsScreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Expense

@Composable
fun EditExpenseScreen(
    expense: Expense,
    onEditExpense: (Expense) -> Unit
) {
    val categories = listOf("Transporte", "Comida", "Alojamiento", "Otros")
    val selectedCategory = remember { mutableStateOf(expense.category) }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val amount = remember { mutableStateOf(expense.amount.toString()) }
    val description = remember { mutableStateOf(expense.description) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Campo para el monto
        OutlinedTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la descripción
        description.value?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { description.value = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
        }



        // Menú desplegable para la categoría
        Text(
            text = "Categoría: ${selectedCategory.value}",
            modifier = Modifier.padding(top = 8.dp)
        )
        Button(onClick = { isDropdownExpanded.value = true }) {
            Text("Seleccionar categoría")
        }
        DropdownMenu(
            expanded = isDropdownExpanded.value,
            onDismissRequest = { isDropdownExpanded.value = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        selectedCategory.value = category
                        isDropdownExpanded.value = false
                    }
                )
            }
        }

        // Botón para guardar los cambios
        Button(
            onClick = {
                if (amount.value.isNotEmpty() ) {
                    onEditExpense(
                        expense.copy(
                            amount = amount.value.toDouble(),
                            description = description.value,
                            category = selectedCategory.value
                        )
                    )
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar cambios")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditExpenseScreen() {
    EditExpenseScreen(
        expense = Expense(
            id_trip = 101,
            amount = 50.0,
            description = "Taxi al aeropuerto",
            category = "Transporte"
        ),
        onEditExpense = { updatedExpense ->
            println("Gasto editado: $updatedExpense")
        }
    )
}