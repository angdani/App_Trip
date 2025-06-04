package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielpons.apptrip.model.Trip
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Notification
import com.danielpons.aplicacionviajes.data.repository.NotificationRepository
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendNotificationScreen(navController: NavController) {
    val tripRepository = TripRepository()
    val userRepository = UserRepository()
    var tripName by remember { mutableStateOf("") }
    val listUsername = remember { mutableStateListOf<String>() }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var filteredUsers by remember { mutableStateOf(listOf<String>()) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        // Cargar los usuarios desde el repositorio
        tripName = tripRepository.getTripNameById(UserSession.idTrip ?: 0).toString()
        listUsername.addAll(userRepository.getUsersNames())
        filteredUsers = listUsername.filter { it.contains(searchQuery.text, ignoreCase = true) }
    }

    LaunchedEffect(searchQuery.text) {
        // Actualizar la lista filtrada cada vez que cambie el texto de búsqueda
        filteredUsers = listUsername.filter { it.contains(searchQuery.text, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Enviar Notificación") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    "Selecciona un usuario y envía una invitación",
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (searchQuery.text.isNotEmpty() && tripName.isNotEmpty()) {
                            scope.launch {
                                sendNotification(searchQuery.text, tripName)
                                navController.popBackStack()
                            }
                        }
                    }, modifier = Modifier
                        .padding(vertical = 16.dp)
                ) {
                    Text("Enviar Invitación")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Buscar usuario", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                        .padding(8.dp),
                    decorationBox = { innerTextField ->
                        if (searchQuery.text.isEmpty()) {
                            Text(
                                "Escribe el nombre del usuario...",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    if (searchQuery.text.isNotEmpty()) {
                        items(filteredUsers) { user ->
                            Text(
                                text = user,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        searchQuery = TextFieldValue(user) // Selecciona el usuario
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Función para enviar la notificación
suspend fun sendNotification(username: String, tripName: String) {
    println("$username te quiere invitar al viaje: $tripName")
    val notification = Notification(
        title = "Invitación a $tripName",
        message = "$username te ha invitado a unirte al viaje: $tripName",
        isRead = false,
        sender = UserSession.userName,
        receiver = username,
        idTrip = UserSession.idTrip,
    )
    NotificationRepository().addNotification(notification)
}

@Preview(showBackground = true)
@Composable
fun SendNotificationScreenPreview() {
    val trip = Trip(
        id_trip = 1,
        name = "Viaje a la playa",
        description = "Un viaje divertido a la playa",
        startDate = "2023-10-01",
        endDate = "2023-10-05"
    )
    SendNotificationScreen(navController = NavController(LocalContext.current))
}