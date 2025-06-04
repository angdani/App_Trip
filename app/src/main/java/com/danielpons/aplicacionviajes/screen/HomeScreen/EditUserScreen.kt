package com.danielpons.aplicacionviajes.screen.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(navController: NavController) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("Editar/Eliminar Usuario") },
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material3.Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* Lógica para editar usuario */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text("Editar Usuario")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Lógica para eliminar usuario */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text("Eliminar Usuario")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditUserScreenPreview() {
    EditUserScreen(navController = rememberNavController())
}