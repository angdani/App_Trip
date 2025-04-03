package com.danielpons.aplicacionviajes.ui.theme.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielpons.aplicacionviajes.ui.theme.BackgroundText // Importa el color que deseas usar

@Composable
fun CustomTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    // Usamos Box para asegurarnos de que el color de fondo se vea correctamente
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Añadimos algo de padding
            .background(BackgroundText) // Color de fondo
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(), // Hace que el TextField ocupe todo el ancho
            label = { Text("Escribe algo") },
            placeholder = { Text("Escribe algo aquí") },
            textStyle = TextStyle(
                color = Color.Black, // Color del texto
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Blue, // Color del texto cuando tiene foco
                unfocusedTextColor = Color.Gray, // Color del texto cuando no tiene foco
                focusedIndicatorColor = Color.Blue, // Color de la línea cuando tiene foco
                unfocusedIndicatorColor = Color.Gray // Color de la línea cuando no tiene foco
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextField() {
    val textState = remember { mutableStateOf(TextFieldValue("Texto de prueba")) }
    CustomTextField(
        value = textState.component1(),
        onValueChange = { textState.value = it }
    )
}
