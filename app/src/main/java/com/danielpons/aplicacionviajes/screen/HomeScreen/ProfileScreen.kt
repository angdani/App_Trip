package com.danielpons.aplicacionviajes.screen.HomeScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.R
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.User
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import com.danielpons.aplicacionviajes.ui.theme.Components.BottomNavigationBar
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.clickable
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        outputFormat.format(date!!)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    val userRepository = UserRepository()
    val userState = remember { androidx.compose.runtime.mutableStateOf<User?>(null) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    LaunchedEffect(Unit) {
        val user = userRepository.getUserByUsername(UserSession.userName ?: "")
        userState.value = user
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (profileImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(profileImageUri),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { showDialog = true },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { showDialog = true },
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userState.value?.username ?: "Usuario",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = userState.value?.email ?: "Correo electrónico no disponible",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (userState.value?.created_at != null) "Usuario desde ${
                        formatDate(
                            userState.value!!.created_at.toString()
                        )
                    }" else "Fecha de creación no disponible",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("edit_userScreen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 24.dp),
                    shape = CircleShape,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Editar perfil",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Gestionar Perfil",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Cambiar foto de perfil") },
                    text = { Text("¿Quieres cambiar tu foto de perfil?") },
                    confirmButton = {
                        Button(onClick = {
                            showDialog = false
                            imagePickerLauncher.launch("image/*")
                        }) {
                            Text("Sí")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}