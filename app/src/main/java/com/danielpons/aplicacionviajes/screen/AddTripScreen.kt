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
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.travelapp.model.TripDto
import kotlinx.coroutines.launch




@Composable
fun AddTripScreen(navController: NavController, onDismiss: () -> Unit) {
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }
    val corroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(16.dp)) {


        // TextField para el Destino
        TextField(value = destination,
            onValueChange = { destination = it },
            label = { Text("Nombre") },
            )
        Spacer(modifier = Modifier.height(8.dp))

        // TextField para la Fecha
        TextField(value = startDate, onValueChange = { startDate = it }, label = { Text("Fecha inicio") })
        Spacer(modifier = Modifier.height(16.dp))
        // TextField para la Fecha
        TextField(value = endDate, onValueChange = { endDate = it }, label = { Text("Fecha fin") })
        Spacer(modifier = Modifier.height(16.dp))


        // Botón para guardar el viaje
        Button(
            onClick = {

                if (destination.isNotEmpty()) {
                    val tripDto: TripDto
                    tripDto = if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                        TripDto(destination, startDate, endDate) // Crear el DTO para el viaje
                    }else{
                        TripDto(destination) // Crear el DTO para el viaje
                    }
                    // Lanzar la corutina
                    corroutineScope.launch {
                        try {
                            // Esperar a que la corutina de la base de datos termine
                            TripRepository().addTrip(tripDto) // Agregar el viaje a la base de datos
                            onDismiss() // Solo se llama después de que la corutina termine
                        } catch (e: Exception) {
                            // Manejo de errores si ocurre un fallo en la corutina
                            println("Error al guardar el viaje: ${e.message}")
                        }
                    }
                } else {
                    isFormValid = false
                }
            },
            enabled = isFormValid
        ) {
            Text("Guardar Viaje")
        }

        // Mostrar un mensaje de error si el formulario no es válido
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






