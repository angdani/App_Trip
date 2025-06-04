package com.danielpons.aplicacionviajes.ui.theme.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.R



data class BudgetCategoryStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val icon: ImageVector
)val foodCategoryStyle = BudgetCategoryStyle(
    backgroundColor = Color(0xFFFFEBEE),
    textColor = Color(0xFFD32F2F),
    icon = Icons.Default.PlayArrow
)

val transportCategoryStyle = BudgetCategoryStyle(
    backgroundColor = Color(0xFFE3F2FD),
    textColor = Color(0xFF1976D2),
    icon = Icons.Default.Build
)

val entertainmentCategoryStyle = BudgetCategoryStyle(
    backgroundColor = Color(0xFFF3E5F5),
    textColor = Color(0xFF7B1FA2),
    icon = Icons.Default.AddCircle
)

@Composable
fun BudgetCategoryItem(
    categoryName: String,
    style: BudgetCategoryStyle,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(style.backgroundColor)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = style.icon,
                contentDescription = null,
                tint = style.textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = categoryName,
                color = style.textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun BudgetCategoryList() {
    val categories = listOf(
        "Comida" to foodCategoryStyle,
        "Transporte" to transportCategoryStyle,
        "Ocio" to entertainmentCategoryStyle
    )

    Column {
        categories.forEach { (name, style) ->
            BudgetCategoryItem(
                categoryName = name,
                style = style,
                onClick = { /* Acción al seleccionar */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBudgetCategoryList() {
    MaterialTheme {
        BudgetCategoryList()
    }
}