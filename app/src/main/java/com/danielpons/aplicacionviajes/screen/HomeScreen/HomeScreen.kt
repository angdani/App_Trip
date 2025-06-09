package com.danielpons.aplicacionviajes.screen.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.danielpons.aplicacionviajes.screen.HomeScreen.TripScreen.AddTripScreen
import com.danielpons.apptrip.model.Trip
import com.danielpons.aplicacionviajes.ui.theme.*
import com.danielpons.aplicacionviajes.ui.theme.Components.AddTripButton
import com.danielpons.aplicacionviajes.ui.theme.Components.BottomNavigationBar
import com.danielpons.aplicacionviajes.ui.theme.Components.DropDown.DropdownMenuOrderTrip
import com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects.TripList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var showAddTripDialog by remember { mutableStateOf(false) }
    val tripRepository = TripRepository()
    val listTrip = remember { mutableStateListOf<Trip>() }
    val userId = UserSession.userId
    var expanded by remember { mutableStateOf(false) } // Controla el menú desplegable
    var sortOption by remember { mutableStateOf("Por defecto") } // Opción seleccionada
    var isRefreshing by remember { mutableStateOf(false) } // Controla el estado de refresco
    val corroutineScope = rememberCoroutineScope()

    val pullToRefreshState = rememberPullToRefreshState()
    suspend fun refreshTrips() {
        isRefreshing = true
        val trips = userId?.let { tripRepository.getTripsByUserId(it) }
        listTrip.clear()
        if (trips != null) {
            listTrip.addAll(trips)
        }
        isRefreshing = false
    }





// Refresca la lista de viajes al iniciar la pantalla
LaunchedEffect(Unit) {
    refreshTrips()
}

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



Scaffold(
floatingActionButton = {
    AddTripButton { showAddTripDialog = true }
},
bottomBar = {
    BottomNavigationBar(navController = navController)
}
) {
    padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //Logo de la app
                Image(
                    painter = painterResource(id = R.drawable.logoapp),
                    contentDescription = "Logo de la app",
                    modifier = Modifier
                        .size(250.dp)
                        .padding()
                )
                //Texto de bienvenida
                Text(
                    text = "¡Crea tus propios viajes!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    modifier = Modifier
                        .padding(bottom = 3.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
        DropdownMenuOrderTrip(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            sortOption = sortOption,
            onSortOptionSelected = { sortOption = it },
            listTrip = listTrip
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 72.dp)
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

