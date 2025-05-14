package com.danielpons.aplicacionviajes.ui.theme.Components.pagerState

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danielpons.aplicacionviajes.data.model.Event
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.*
import kotlinx.datetime.Instant

@Composable
fun PagerScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 }) // 3 páginas


    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
0 -> TripOverviewScreen(
                    tripTitle = "Viaje a la montaña",
                    events = listOf(
                        Event(
                            id_event = 1,
                            id_trip = 1,
                            title = "Excursión",
                            description = "Caminata por el sendero principal.",
                            start_datetime = Instant.parse("2023-11-10T09:00:00Z"),
                            cost = 0.0
                        ),
                        Event(
                            id_event = 2,
                            id_trip = 1,
                            title = "Cena en refugio",
                            description = "Cena grupal en el refugio de montaña.",
                            start_datetime = kotlinx.datetime.Instant.parse("2023-11-10T19:00:00Z"),
                            cost = 30.0
                        )
                    ),
                    totalExpenses = 30.0,
                    tripDate = "2023-11-10",
                    budgetPercentage = 0.5f,
                    pointsOfInterest = listOf("Mirador", "Cascada", "Refugio"),
                    tasks = listOf(
                        "Preparar mochila" to true,
                        "Comprar snacks" to false,
                        "Revisar clima" to true
                    ),
                    onTaskCheckedChange = { _, _ -> }
                )
                1 -> BudgetScreen(totalBudget = 100.0)
                2 -> MapScreen(/* ... */)
            }
        }
        // Puedes agregar indicadores o controles aquí si lo deseas
    }
}