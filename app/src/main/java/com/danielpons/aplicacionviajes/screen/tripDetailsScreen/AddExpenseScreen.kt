package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Expense
import com.danielpons.aplicacionviajes.data.repository.ExpenseRepository
import kotlinx.coroutines.launch

@Composable
fun AddExpenseScreen(
    onAddExpense: (Expense) -> Unit,
) {
    val categories = listOf("Transporte", "Comida", "Alojamiento")
    val selectedCategory = remember { mutableStateOf("") }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val amount = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val expenseRepository = ExpenseRepository()
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Campo para el costo
        OutlinedTextField(
            value = amount.value,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() || it == '.' }) {
                    amount.value = newValue
                }
            },
            label = { Text("Coste") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        // Campo para la descripción
        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )


        // Menú desplegable para la categoría
        Text(
            text = "Categoría: ${selectedCategory.value.ifEmpty { "Seleccionar" }}",
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

        // Botón para agregar el gasto
        Button(
            onClick = {
                if (amount.value.isNotEmpty() && description.value.isNotEmpty() &&   selectedCategory.value.isNotEmpty()) {
                    onAddExpense(
                        Expense(
                            id_trip = 0, // Ajustar según lógica de tu aplicación
                            amount = amount.value.toDouble(),
                            description = description.value,
                            category = selectedCategory.value
                        )

                    )
                    coroutineScope.launch {
                        expenseRepository.addExpense(
                            expense = Expense(
                                id_trip = UserSession.idTrip?: 0,
                                amount = amount.value.toDouble(),
                                description = description.value,
                                category = selectedCategory.value

                            )
                        )

                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Agregar gasto")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddExpenseScreen() {
    AddExpenseScreen(onAddExpense = { expense ->
        println("Gasto añadido: $expense")
    })
}