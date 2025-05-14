package com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent.TripItem
import com.danielpons.apptrip.model.Trip

@Composable
fun TripList(
    navController: NavController,
    trips: List<Trip>,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(25.dp)) // Espaciado superior
        }
        items(trips) { trip ->
            // Para cada viaje en la lista, se muestra un item clickeable
            TripItem(trip) { selectedTrip ->
                navController.navigate("trip_pager/${trip.id_trip}")
            }
        }
    }
}