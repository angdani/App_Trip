package com.danielpons.aplicacionviajes.ui.theme.Components.DropDown

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.danielpons.aplicacionviajes.R
import com.danielpons.apptrip.model.Trip

@Composable
fun DropdownMenuOrderTrip(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    sortOption: String,
    onSortOptionSelected: (String) -> Unit,
    listTrip: MutableList<Trip>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ordenar: $sortOption",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.5f)
        )
        IconButton(onClick = { onExpandedChange(true) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = "Ordenar"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(
                text = { Text("Por nombre") },
                onClick = {
                    onExpandedChange(false)
                    onSortOptionSelected("Por nombre")
                    listTrip.sortBy { it.name }
                }
            )
            DropdownMenuItem(
                text = { Text("Por fecha de inicio") },
                onClick = {
                    onExpandedChange(false)
                    onSortOptionSelected("Por fecha de inicio")
                    listTrip.sortBy { it.startDate }
                }
            )
            DropdownMenuItem(
                text = { Text("Por fecha de fin") },
                onClick = {
                    onExpandedChange(false)
                    onSortOptionSelected("Por fecha de fin")
                    listTrip.sortBy { it.endDate }
                }
            )
        }
    }
}

