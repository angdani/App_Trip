package com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.data.repository.TaskRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
) {
    var taskRepository = TaskRepository()
    val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.is_completed,
                onCheckedChange = { isChecked ->
                    onCheckedChange(isChecked)
                    task.is_completed = isChecked
                    coroutineScope.launch {
                        taskRepository.updateTask(
                            task
                        )
                    }
                }
            )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge
            )
            task.due_date?.let {
                Text(
                    text = "Vence: ${formatFecha(it)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun formatFecha(fecha: String): String {
    val localDate = LocalDate.parse(fecha) // Asume formato "yyyy-MM-dd"
    val formatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", Locale("es"))
    return localDate.format(formatter).replaceFirstChar { it.uppercase() }
}

@Composable
@Preview(showBackground = true)
fun PreviewTaskItem() {
    TaskItem(
        task = Task(
            id_task = 1,
            id_trip = 1,
            title = "Preparar mochila",
            due_date = "2024-06-10",
            is_completed = false
        ),
        onCheckedChange = {}
    )
}