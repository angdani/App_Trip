package com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.R
import com.danielpons.aplicacionviajes.data.model.Budget
import androidx.compose.material3.Icon

import androidx.compose.foundation.clickable

@Composable
fun BudgetItem(
    budget: Budget,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .clickable { onClick() }, // Hacer clic en el elemento
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono representativo de la categoría
        Icon(
            painter = when (budget.category) {
                "Transporte" -> painterResource(id = R.drawable.icon_transport_budget)
                "Comida" -> painterResource(id = R.drawable.icon_food_budget)
                "Alojamiento" -> painterResource(id = R.drawable.icon_accommodation_budget)
                else -> painterResource(id = R.drawable.icon_default_budget)
            },
            contentDescription = budget.category,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Información de la categoría y el total de gastos
        Column {
            Text(
                text = budget.category,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Total: ${budget.actual_amount} ${budget.currency}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBudgetItem() {
    BudgetItem(
        budget = Budget(
            id_budget = 1,
            category = "Comida",
            planned_amount = 200.0,
            actual_amount = 150.0,
            currency = "USD",
            notes = "Taxi y autobuses"
        ),
        onClick = { println("Presupuesto seleccionado: Comida") } // Acción de ejemplo
    )
}
