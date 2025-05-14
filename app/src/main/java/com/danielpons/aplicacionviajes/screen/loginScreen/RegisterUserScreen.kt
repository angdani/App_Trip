package com.danielpons.aplicacionviajes.screen.loginScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.R
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import kotlinx.coroutines.launch


// Asegúrate de inicializar tu UserRepository correctamente
@Composable
fun RegisterUserScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }
    val userRepository = UserRepository()
    IconButton(onClick = { navController.navigate("login") }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Volver"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Logo de la app
        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )
        // Texto grande
        Text(
            text = "Regístrate en tu App preferida de viajes",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.ic_visibility_on)
                else painterResource(id = R.drawable.ic_visibility_off)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    coroutineScope.launch {
                        // Aquí puedes conectar la lógica de registro con tu UserRepository
                        userRepository.registerUser(username.trim(), email.trim(), password.trim())
                        val registrationSuccess = true // Simula el resultado del registro
                        if (registrationSuccess) {
                            //lanza un mensaje de éxito
                            Toast.makeText(
                                navController.context,
                                "Registro exitoso",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("login") // Navega a la pantalla de inicio de sesión
                        } else {
                            isFormValid = false
                        }
                    }
                } else {
                    isFormValid = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        if (!isFormValid) {
            Text(
                text = "Por favor, complete todos los campos correctamente.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterUserPreview() {
    RegisterUserScreen(
        navController = rememberNavController(),
    )
}