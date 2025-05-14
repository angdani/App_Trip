package com.danielpons.aplicacionviajes.screen.HomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.R
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.aplicacionviajes.screen.AddTripScreen
import com.danielpons.apptrip.model.Trip
import com.danielpons.aplicacionviajes.ui.theme.*
import com.danielpons.aplicacionviajes.ui.theme.Components.AddTripButton
import com.danielpons.aplicacionviajes.ui.theme.Components.BottomNavigationBar
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.TripList
import io.github.jan.supabase.auth.SessionManager

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController) {
    var showAddTripDialog by remember { mutableStateOf(false) }
     val tripRepository = TripRepository()
    val listTrip = remember { mutableStateListOf<Trip>() }
    val userId = UserSession.userId
    LaunchedEffect(Unit) {
        val trips = userId?.let { tripRepository.getTripsByUserId(it) }// Llama a la función suspend
        if (trips != null) {
            listTrip.addAll(trips)
        } // Actualiza la lista
    }


    // --- Dialog emergente ---
    if (showAddTripDialog) {
        Dialog(onDismissRequest = { showAddTripDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
            ) {
                AddTripScreen(
                    navController = navController,
                    onDismiss = { showAddTripDialog = false }
                )
            }
        }
    }

//    val listTrip = listOf(
//        Trip(
//            id_trip = 1,
//            name = "Viaje a la playa",
//            startDate = "2023-10-01",
//            endDate = "2023-10-05",
//            description = "Un viaje relajante a la playa.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 2,
//            name = "Viaje a la montaña",
//            startDate = "2023-11-01",
//            endDate = "2023-11-05",
//            description = "Un viaje emocionante a la montaña.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 3,
//            name = "Viaje a la ciudad",
//            startDate = "2023-12-01",
//            endDate = "2023-12-03",
//            description = "Un viaje cultural a la ciudad.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 4,
//            name = "Viaje al desierto",
//            startDate = "2024-01-10",
//            endDate = "2024-01-15",
//            description = "Una aventura en el desierto.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 5,
//            name = "Viaje al bosque",
//            startDate = "2024-02-20",
//            endDate = "2024-02-25",
//            description = "Exploración en el bosque.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 6,
//            name = "Viaje al lago",
//            startDate = "2024-03-15",
//            endDate = "2024-03-20",
//            description = "Relajación junto al lago.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//
//        Trip(
//            id_trip = 7,
//            name = "Viaje al lago",
//            startDate = "2024-03-15",
//            endDate = "2024-03-20",
//            description = "Relajación junto al lago.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 8,
//            name = "Viaje al lago",
//            startDate = "2024-03-15",
//            endDate = "2024-03-20",
//            description = "Relajación junto al lago.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//        Trip(
//            id_trip = 9,
//            name = "Viaje al lago",
//            startDate = "2024-03-15",
//            endDate = "2024-03-20",
//            description = "Relajación junto al lago.",
//            cover_image_url = null,
//            status = Trip.TripStatus.PLANNED
//        ),
//    )

    Scaffold(
        floatingActionButton = {
            AddTripButton { showAddTripDialog = true }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Box para logo y frase
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logoapp),
                        contentDescription = "Logo de la app",
                        modifier = Modifier
                            .size(250.dp)
                            .padding(top = 16.dp, bottom = 8.dp)
                    )
                    Text(
                        text = "¡Crea tus propios viajes!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Box para la lista de viajes, ocupa el resto del espacio
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 72.dp) // Espacio para el FAB
            ) {
                if (listTrip.isEmpty()) {
                    Text(
                        text = "No hay viajes disponibles",
                        style = MaterialTheme.typography.bodyLarge,
                        color = DarkGray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    TripList(navController, listTrip, PaddingValues(0.dp))
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}

