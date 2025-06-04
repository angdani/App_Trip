package com.danielpons.aplicacionviajes.ui.theme.Components.DropDown

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DropDownMenuDeleteEdit(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onEditTrip: () -> Unit,
    onDeleteTrip: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Editar") },
            onClick = onEditTrip
        )
        DropdownMenuItem(
            text = { Text("Eliminar") },
            onClick = {
                coroutineScope.launch {
                    onDeleteTrip()
                }
                onDismissRequest()
            }
        )
    }
}
