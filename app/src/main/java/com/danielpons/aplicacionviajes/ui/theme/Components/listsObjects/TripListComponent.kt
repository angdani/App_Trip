package com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects

import TripItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.aplicacionviajes.screen.HomeScreen.TripScreen.EditTripScreen
import com.danielpons.aplicacionviajes.ui.theme.Components.DropDown.DropDownMenuDeleteEdit
import com.danielpons.apptrip.model.Trip
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TripList(
    navController: NavController,
    trips: List<Trip>,
    padding: PaddingValues,
) {
    var showEditTripDialog by remember { mutableStateOf(false) }
    var selectedTrip by remember { mutableStateOf<Trip?>(null) }
    val tripRepository = TripRepository()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // --- Dialog emergente ---
    if (showEditTripDialog && selectedTrip != null) {
        Dialog(onDismissRequest = { showEditTripDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
            ) {
                EditTripScreen(
                    navController = navController,
                    trip = selectedTrip!!, // Pasamos el viaje seleccionado
                    onDismiss = { showEditTripDialog = false }
                )
            }
        }
    }



    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(25.dp)) // Espaciado superior
        }
        items(trips) { trip ->
            var expanded by remember { mutableStateOf(false) }

            // Para cada viaje en la lista, se muestra un item clickeable
            TripItem(
                trip = trip,
                modifier = Modifier.combinedClickable(
                    onClick = {
                        UserSession.idTrip = trip.id_trip
                        navController.navigate("trip_pager/${trip.id_trip}")
                    },
                    onLongClick = {
                        UserSession.idTrip = trip.id_trip
                        expanded = true
                    }
                ),
                onClick = {
                    UserSession.idTrip = trip.id_trip
                    navController.navigate("trip_pager/${trip.id_trip}")
                }
            )

            DropDownMenuDeleteEdit(
                expanded = expanded,
                onDismissRequest = { expanded = false },
             onDeleteTrip = {
                               coroutineScope.launch {
                                   trip.id_trip?.let { id ->
                                       tripRepository.deleteTripById(id)
                                       android.widget.Toast.makeText(
                                           context,
                                           "Viaje eliminado con éxito",
                                           android.widget.Toast.LENGTH_SHORT
                                       ).show()
                                   }
                               }
                               expanded = false
                           },
                onEditTrip = {
                    expanded = false
                    selectedTrip = trip // Guardamos el viaje seleccionado
                    showEditTripDialog = true // Mostramos el diálogo de edición
                })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTripList() {
    val navController = TestNavHostController(LocalContext.current)
    val sampleTrips = listOf(
        Trip(id_trip = 1, name = "Viaje a París", startDate = "2023-10-01", endDate = "2023-10-10"),
        Trip(
            id_trip = 2,
            name = "Viaje a Nueva York",
            startDate = "2023-11-05",
            endDate = "2023-11-15"
        )
    )
    TripList(navController = navController, trips = sampleTrips, padding = PaddingValues(16.dp))
}