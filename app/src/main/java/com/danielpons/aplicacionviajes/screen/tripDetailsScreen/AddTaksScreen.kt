package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.global.UserSession
import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.data.repository.TaskRepository

@Composable
fun AddTaskScreen(onDismiss: () -> Unit, onTaskAdded: (Task) -> Unit) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isAddingTask by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val taskRepository = TaskRepository()

    if (isAddingTask) {
        LaunchedEffect(Unit) {
            val newTask = Task(
                id_task = null, // Se genera automáticamente
                id_trip = UserSession.idTrip ?: return@LaunchedEffect,
                title = taskTitle,
                due_date = taskDueDate,
                is_completed = false
            )
            try {
                taskRepository.addTask(newTask)
                onTaskAdded(newTask)
                Toast.makeText(context, "Tarea añadida correctamente", Toast.LENGTH_SHORT).show()
                onDismiss()
            } catch (e: Exception) {
                errorMessage = "Error al añadir la tarea: ${e.message}"
            } finally {
                isAddingTask = false
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Título de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = taskDueDate,
                onValueChange = { taskDueDate = it },
                label = { Text("Fecha límite (yyyy-MM-dd)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            IconButton(onClick = { showDatePicker(context) { taskDueDate = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                errorMessage = null
                if (taskTitle.isEmpty() || !isValidDate(taskDueDate)) {
                    errorMessage = "Por favor complete todos los campos correctamente."
                } else {
                    isAddingTask = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir Tarea")
        }

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddTaskScreen() {
    AddTaskScreen(onDismiss = {}, onTaskAdded = {})
}