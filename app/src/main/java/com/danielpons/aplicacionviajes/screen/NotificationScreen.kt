package com.danielpons.aplicacionviajes.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Notification
import com.danielpons.aplicacionviajes.data.model.TripParticipant
import com.danielpons.aplicacionviajes.data.repository.NotificationRepository
import com.danielpons.aplicacionviajes.data.repository.TripParticipantRepository
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import com.danielpons.aplicacionviajes.ui.theme.Components.BottomNavigationBar
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    val notificationRepository = NotificationRepository()
    val userRepository = UserRepository()
    val tripParticipantRepository = TripParticipantRepository()
    val notifications = remember { mutableStateListOf<Notification>() } // Recordar el estado
    var idUserRecive: String? = null
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        UserSession.userName?.let { userName ->
            val result = notificationRepository.getNotificationsByUserName(userName)
            notifications.clear() // Asegurarse de que no haya duplicados
            notifications.addAll(result) // Agregar las notificaciones

        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones") }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes notificaciones")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(notifications, key = { it.id!! }) { notification ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = notification.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = " ${notification.createAt}",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = notification.message,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                Log.d(
                                                    "NotificationScreen",
                                                    "Intentando aceptar notificación con ID: ${notification.id}"
                                                )

                                                    tripParticipantRepository.addParticipant(
                                                        TripParticipant(
                                                            id_trip = notification.idTrip ?: 0,
                                                            id_user = UserSession.userId,
                                                            is_dirty = true,
                                                            role = "viewer"
                                                        )
                                                    )
                                                    Log.d(
                                                        "NotificationScreen",
                                                        "Participante agregado correctamente"
                                                    )
                                                notificationRepository.deleteNotification(
                                                    notification.id!!
                                                )
                                                notifications.removeIf { it.id == notification.id }
                                                Log.d(
                                                    "NotificationScreen",
                                                    "Notificación aceptada correctamente"
                                                )
                                            } catch (e: Exception) {
                                                Log.e(
                                                    "NotificationScreen",
                                                    "Error al aceptar notificación: ${e.message}",
                                                    e
                                                )
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text("Aceptar")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedButton(
                                    onClick = {
                                        scope.launch {
                                            try {
                                                Log.d(
                                                    "NotificationScreen",
                                                    "Intentando rechazar notificación con ID: ${notification.id}"
                                                )


                                                notificationRepository.deleteNotification(
                                                    notification.id!!
                                                )
                                                notifications.removeIf { it.id == notification.id }
                                                Log.d(
                                                    "NotificationScreen",
                                                    "Notificación rechazada correctamente"
                                                )
                                            } catch (e: Exception) {
                                                Log.e(
                                                    "NotificationScreen",
                                                    "Error al rechazar notificación: ${e.message}",
                                                    e
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Text("Rechazar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewNotificationScreen() {
    val navController = rememberNavController()
    NotificationScreen(navController = navController)
}

