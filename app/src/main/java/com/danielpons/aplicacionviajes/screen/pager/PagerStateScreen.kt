package com.danielpons.aplicacionviajes.ui.theme.Components.pagerState

import BudgetScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Event
import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.data.repository.EventRepository
import com.danielpons.aplicacionviajes.data.repository.TaskRepository
import com.danielpons.aplicacionviajes.data.repository.TripRepository
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.TripDetailsScreen
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.MapScreen

@Composable
fun PagerScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 }) // 3 páginas
    val eventRepository = EventRepository()
    val taskRepository = TaskRepository()
    val tripRepository = TripRepository()
    val events = remember { mutableStateListOf<Event>() }
    val tasks = remember { mutableStateListOf<Task>() }
    val tripName = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        UserSession.idTrip?.let { eventRepository.getTripsByUserId(it) }
        events.addAll(eventRepository.getTripsByUserId(UserSession.idTrip!!))
        tasks.addAll(taskRepository.getTasksByTripId(UserSession.idTrip!!))
        tripName.value = tripRepository.getTripNameById(UserSession.idTrip!!).toString()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(1.dp)
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> TripDetailsScreen(
                    navController = navController,
                    tripTitle = tripName.value,
                    events = events,
                    pointsOfInterest = listOf("Mirador", "Cascada", "Refugio"),
                    tasks = tasks,
                    onTaskCheckedChange = { taskId, isChecked ->
                        val index = tasks.indexOfFirst { it.id_task == taskId }
                        if (index != -1) {
                            val updatedTask = tasks[index].copy(is_completed = isChecked)
                            tasks[index] = updatedTask
                        }
                    }
                )

                1 -> BudgetScreen()

                2 -> MapScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPagerScreen() {
    val navController = rememberNavController()
    PagerScreen(navController = navController)
}