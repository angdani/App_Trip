package com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import com.danielpons.aplicacionviajes.data.model.Budget
import com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent.BudgetItem

@Composable
fun BudgetList(
    budgets: List<Budget>,
    onCategorySelected: (String) -> Unit // Función para manejar la selección de categoría
) {
    LazyRow(
        modifier = Modifier
            .padding(8.dp)
    ) {
        items(budgets.size) { index ->
            BudgetItem(
                budget = budgets[index],
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp), // Espaciado interno
                onClick = { onCategorySelected(budgets[index].category) } // Llama a la función al pulsar
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBudgetHorizontalList() {
    val sampleBudgets = listOf(
        Budget(id_budget = 1, category = "Transporte", planned_amount = 200.0, actual_amount = 150.0, currency = "USD"),
        Budget(id_budget = 2,category = "Comida", planned_amount = 100.0, actual_amount = 80.0, currency = "USD"),
        Budget(id_budget = 3, category = "Alojamiento", planned_amount = 300.0, actual_amount = 250.0, currency = "USD")
    )

BudgetList(
            budgets = sampleBudgets,
            onCategorySelected = {} // Puedes poner una lambda vacía o una acción de ejemplo
        )
    }
