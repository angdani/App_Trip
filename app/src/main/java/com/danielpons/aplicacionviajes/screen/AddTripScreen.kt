package com.danielpons.aplicacionviajes.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.data.connection.SupabaseConnection
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import kotlinx.coroutines.launch



@Composable
fun AddTripScreen(navController: NavController, onDismiss: () -> Unit) {
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }

    fun isValidDate(date: String): Boolean {
        return date.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = destination,
            onValueChange = { destination = it },
            label = { Text("Destino") },
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Fecha inicio (yyyy-MM-dd)") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = endDate,
            onValueChange = { endDate = it },
            label = { Text("Fecha fin (yyyy-MM-dd)") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (destination.isNotEmpty() && isValidDate(startDate) && isValidDate(endDate)) {
                    println("Viaje guardado: $destination, $startDate, $endDate")
                    onDismiss()
                } else {
                    isFormValid = false
                }
            },
            enabled = isFormValid
        ) {
            Text("Guardar Viaje")
        }

        if (!isFormValid) {
            Text(text = "Por favor complete todos los campos correctamente.", color = MaterialTheme.colorScheme.error)
        }
    }
}
@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun AddTripScreenPreview() {
    AddTripScreen(
        navController = rememberNavController(),
        onDismiss = {}
    )
}
