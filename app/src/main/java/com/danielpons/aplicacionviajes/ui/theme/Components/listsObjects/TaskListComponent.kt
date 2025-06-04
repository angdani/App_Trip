package com.danielpons.aplicacionviajes.ui.theme.Components.listsObjects

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.data.repository.TaskRepository
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.AddTaskScreen
import com.danielpons.aplicacionviajes.screen.tripDetailsScreen.EditTaskScreen
import com.danielpons.aplicacionviajes.ui.theme.Components.DropDown.DropDownMenuDeleteEdit
import com.danielpons.aplicacionviajes.ui.theme.Components.itemComponent.TaskItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskListSection(
    initialTasks: List<Task>,
    onTaskCheckedChange: (Int, Boolean) -> Unit,
) {
    val taskRepository = TaskRepository()
    var tasks by remember { mutableStateOf(initialTasks) }
    var showAddTask by remember { mutableStateOf(false) }
    var showEditTaskDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    // Dialog para agregar tarea
    if (showAddTask) {
        Dialog(onDismissRequest = { showAddTask = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                AddTaskScreen(
                    onDismiss = { showAddTask = false },
                    onTaskAdded = { newTask ->
                        tasks = tasks + newTask
                        showAddTask = false
                    }
                )
            }
        }
    }

    // Dialog para editar tarea
    if (showEditTaskDialog) {
        Dialog(onDismissRequest = { showEditTaskDialog = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
            ) {
                EditTaskScreen(
                    onDismiss = { showEditTaskDialog = false },
                    selectedTask = selectedTask ?: Task(0, 0, "", null, false),
                )
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .heightIn(max = 325.dp),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Lista de tareas:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (tasks.isEmpty()) {
                Text(
                    text = "No hay tareas pendientes.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp)
                ) {
                    items(tasks.size) { index ->
                        var taskExpanded by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier.combinedClickable(
                                onClick = { taskExpanded = true }
                            )
                        ) {
                            TaskItem(
                                task = tasks[index],
                                onCheckedChange = { checked ->
                                    val updatedTask = tasks[index].copy(is_completed = checked)
                                    tasks =
                                        tasks.toMutableList().apply { this[index] = updatedTask }
                                    onTaskCheckedChange(
                                        updatedTask.id_task ?: 0,
                                        updatedTask.is_completed
                                    )

                                }
                            )

                            DropDownMenuDeleteEdit(
                                expanded = taskExpanded,
                                onDismissRequest = { taskExpanded = false },
                                onEditTrip = {
                                    selectedTask = tasks[index]
                                    showEditTaskDialog = true
                                    taskExpanded = false
                                },
                                onDeleteTrip = {
                                    tasks[index].id_task?.let { taskId ->
                                        taskRepository.deleteTask(taskId)
                                        tasks = tasks.filter { it.id_task != taskId }
                                    }
                                    taskExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { showAddTask = true }
                ) {
                    Text(
                        text = "Agregar tarea",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskListSection() {
    TaskListSection(
        initialTasks = listOf(
            Task(
                id_task = 1,
                id_trip = 1,
                title = "Preparar mochila",
                due_date = "2024-06-10",
                is_completed = false
            ),
            Task(
                id_task = 2,
                id_trip = 1,
                title = "Comprar snacks",
                due_date = "2024-06-11",
                is_completed = true
            ),
            Task(
                id_task = 3,
                id_trip = 1,
                title = "Revisar clima",
                due_date = "2024-06-12",
                is_completed = false
            )
        ),
onTaskCheckedChange = { _, _ -> }
       )
}