package com.danielpons.aplicacionviajes.screen.tripDetailsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.data.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun EditTaskScreen(onDismiss: () -> Unit, selectedTask: Task) {
    var taskTitle by remember { mutableStateOf(selectedTask.title) }
    var taskDueDate by remember { mutableStateOf(selectedTask.due_date) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val taskRepository = TaskRepository()
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            label = { Text("Título de la tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            taskDueDate?.let {
                TextField(
                    value = it,
                    onValueChange = { taskDueDate = it },
                    label = { Text("Fecha límite (yyyy-MM-dd)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            IconButton(onClick = { showDatePicker(context) { taskDueDate = it } }) {
                Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                errorMessage = null
                if (taskTitle.isEmpty() || !taskDueDate?.let { isValidDate(it) }!!) {
                    errorMessage = "Por favor complete todos los campos correctamente."
                } else {
                    val updatedTask = selectedTask.copy(
                        title = taskTitle,
                        due_date = taskDueDate
                    )
                    try {

                 CoroutineScope(Dispatchers.IO).launch {
                     taskRepository.updateTask(updatedTask)
                 }
                        Toast.makeText(context, "Tarea actualizada correctamente", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    } catch (e: Exception) {
                        errorMessage = "Error al actualizar la tarea: ${e.message}"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar Tarea")
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
fun PreviewEditTaskScreen() {
    val sampleTask = Task(
        id_task = 1,
        id_trip = 1,
        title = "Preparar mochila",
        due_date = "2024-06-10",
        is_completed = false
    )

    EditTaskScreen(
        onDismiss = {},
        selectedTask = sampleTask,
    )
}