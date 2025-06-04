import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Budget
import com.danielpons.aplicacionviajes.data.model.Expense
import com.danielpons.aplicacionviajes.data.repository.ExpenseRepository
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.BudgetList
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.ExpenseListScreen


fun calculateTotalExpensesByCategory(expenses: List<Expense>, category: String): Double {
    return expenses.filter { it.category == category }.sumOf { it.amount}
}
fun calculateTotalExpenses(expenses: List<Expense>): Double {
    return expenses.sumOf { it.amount}
}

@Composable
fun BudgetScreen() {
    val expenseRepository = ExpenseRepository()
    val coroutineScope = rememberCoroutineScope()

    var listExpenses by remember { mutableStateOf<List<Expense>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val expenses = expenseRepository.getExpensesByTripId(UserSession.idTrip ?: 0)
        listExpenses = expenses
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Cargando gastos...")
        }
    } else {
        val budgetList = listOf(
            Budget(
                id_budget = 1,
                category = "Total",
                planned_amount = calculateTotalExpenses(listExpenses),
                actual_amount = calculateTotalExpenses(listExpenses),
                currency = "€"
            ),
            Budget(
                id_budget = 2,
                category = "Transporte",
                planned_amount = calculateTotalExpensesByCategory(listExpenses, "Transporte"),
                actual_amount = calculateTotalExpensesByCategory(listExpenses, "Transporte"),
                currency = "€"
            ),
            Budget(
                id_budget = 3,
                category = "Comida",
                planned_amount = calculateTotalExpensesByCategory(listExpenses, "Comida"),
                actual_amount = calculateTotalExpensesByCategory(listExpenses, "Comida"),
                currency = "€"
            ),
            Budget(
                id_budget = 4,
                category = "Alojamiento",
                planned_amount = calculateTotalExpensesByCategory(listExpenses, "Alojamiento"),
                actual_amount = calculateTotalExpensesByCategory(listExpenses, "Alojamiento"),
                currency = "€"
            )
        )

        var selectedCategory by remember { mutableStateOf<String?>(null) }

        val filteredExpenses = remember(selectedCategory) {
            listExpenses.filter { selectedCategory == null || it.category == selectedCategory }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            BudgetList(
                budgets = budgetList,
                onCategorySelected = { category ->
                    selectedCategory = category
                }
            )

            ExpenseListScreen(initialExpenses = filteredExpenses)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { /* Acción para agregar gastos */ }) {
                    Text(text = "Agregar gastos")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBudgetScreen() {
    BudgetScreen()
}