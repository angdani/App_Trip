package com.danielpons.aplicacionviajes.screen.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.aplicacionviajes.screen.AddTripScreen
import com.danielpons.aplicacionviajes.ui.theme.* // Aquí importamos los colores personalizados
import com.danielpons.aplicacionviajes.ui.theme.Components.AddTripButton
import com.danielpons.travelapp.model.TripDto

// Composable principal de la pantalla de inicio
@Composable
 fun HomeScreen(navController: NavController) {
    // Se obtiene la lista de viajes del ViewModel
    var listTrip by remember { mutableStateOf<List<TripDto>>(emptyList()) }
    // Estado para mostrar o no el formulario de añadir viaje
    var showAddTripForm by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Llama a la función de consulta dentro de LaunchedEffect para evitar múltiples ejecuciones
    LaunchedEffect(Unit) {
        listTrip = TripRepository().selectUserTrips()
    }

    Scaffold(
        floatingActionButton = { AddTripButton { navController.navigate("add_trip") } } // Botón flotante para añadir viaje
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Se muestra la lista de viajes
            TripList(navController, listTrip, padding)
        }
    }
}



// Lista de viajes mostrados en una columna desplazable
@Composable
fun TripList(navController: NavController,
             trips: List<TripDto>,
             padding: PaddingValues
             ) {

    LazyColumn(modifier = Modifier.padding(padding)) {
        item {
            Spacer(modifier = Modifier.height(25.dp)) // Espaciado superior
        }
        items(trips) { trip ->
            // Para cada viaje en la lista, se muestra un item clickeable
            TripItem(trip) { selectedTrip ->
                navController.navigate("trip_details/${selectedTrip.id}") // Navega a la pantalla de detalles del viaje
            }
        }
    }
}

// Caja que contiene el formulario de agregar viaje
@Composable
fun AddTripFormInBox(
    navController: NavController,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(400.dp) // Definir el tamaño de la caja
            .background(Blue, shape = RoundedCornerShape(16.dp)) // Color azul para el formulario
            .padding(16.dp)
    ) {
        AddTripScreen(navController, onDismiss) // Aquí se muestra el formulario real de añadir viaje
    }
}

// Elemento individual que representa un viaje en la lista
@Composable
fun TripItem(trip: TripDto, onClick: (TripDto) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(trip) }
            .background(Blue.copy(alpha = 0.1f)) // Fondo azul suave para cada item
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = trip.name, style = MaterialTheme.typography.bodyLarge, color = DarkGray) // Texto en gris oscuro
            Text(text = trip.startDate.toString(), style = MaterialTheme.typography.bodyMedium, color = DarkGray) // Texto en gris oscuro
        }
    }
}

// Vista previa de la pantalla de inicio para la herramienta de previsualización de Jetpack Compose
@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun PreviewHomeScreen() {
    // Este es un ejemplo de cómo ver la pantalla en un preview
    HomeScreen(
        navController = rememberNavController(),
    ) // Usa rememberNavController aquí
}
