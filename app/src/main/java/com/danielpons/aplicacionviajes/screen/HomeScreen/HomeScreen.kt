package com.danielpons.aplicacionviajes.screen.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.apptrip.model.Trip
import com.danielpons.aplicacionviajes.ui.theme.*
import com.danielpons.aplicacionviajes.ui.theme.Components.AddTripButton
import com.danielpons.aplicacionviajes.ui.theme.Components.BottomNavigationBar
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.TripList

@Composable
fun HomeScreen(navController: NavController) {
    val listTrip = listOf(
      Trip(
            id_trip = 1,
            name = "Viaje a la playa",
            startDate = "2023-10-01",
            endDate = "2023-10-05",
            description = "Un viaje relajante a la playa.",
            cover_image_url = null,
            status = Trip.TripStatus.PLANNED
        ),
        Trip(
            id_trip = 2,
            name = "Viaje a la montaña",
            startDate = "2023-11-01",
            endDate = "2023-11-05",
            description = "Un viaje emocionante a la montaña.",
            cover_image_url = null,
            status = Trip.TripStatus.PLANNED
        )
    )

    Scaffold(
        floatingActionButton = {
            AddTripButton { navController.navigate("add_trip") }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }


    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (listTrip.isEmpty()) {
                Text(
                    text = "No hay viajes disponibles",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkGray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                TripList(navController, listTrip, padding)
            }
        }
    }
}





@Composable
@SuppressLint("ViewModelConstructorInComposable")
@Preview
fun PreviewHomeScreen() {
    // Este es un ejemplo de cómo ver la pantalla en un preview
    HomeScreen(
        navController = rememberNavController(),
    ) // Usa rememberNavController aquí
}
