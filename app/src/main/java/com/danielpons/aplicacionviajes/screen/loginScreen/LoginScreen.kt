package com.danielpons.aplicacionviajes.screen.loginScreen


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danielpons.aplicacionviajes.R
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.User
import com.danielpons.aplicacionviajes.data.repository.UserRepository
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFormValid by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }
    val userRepository = UserRepository()
    var user : User?
    var loginSuccess = false

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
                .size(200.dp) // Ajusta el tamaño según sea necesario
                .padding(bottom = 16.dp)
        )
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
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
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    coroutineScope.launch {
                        //  lógica de inicio de sesión
                      user =  userRepository.getUserRegistered(username, password)
                        if(user != null) {
                          UserSession.userId = user?.id_user
                           android.util.Log.d("LoginScreen", "UserSession.userId actualizado a: ${UserSession.userId}")
                             loginSuccess = true
                        }
                        if (loginSuccess) {
                            onLoginSuccess()
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
            Text("Iniciar sesión")
        }

        if (!isFormValid) {
            Text(
                text = "Por favor, complete todos los campos correctamente.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { navController.navigate("register_user") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿Aún no estás registrado? Regístrate aquí",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController(),
        onLoginSuccess = {}
    )
}