package com.danielpons.aplicacionviajes.data.mainTest

import com.danielpons.aplicacionviajes.data.model.Task
import com.danielpons.aplicacionviajes.data.repository.TaskRepository

suspend fun main() {
    val taskRepository = TaskRepository()
    var taskList = listOf<Task>()
    var task = Task(
        id_task = 12, // Dejar nulo para que se genere automáticamente
        id_trip = 21, // Cambia el ID del viaje según sea necesario
        title = "Acrtualizar tarea de prueba",
        due_date = "2023-10-31",
        is_completed = false,
    )

    taskList = taskRepository.getTasksByTripId(21) // Cambia el ID del viaje según sea necesario
    println("Ejecutando pruebas de TaskRepository...")

    println(taskList .joinToString("\n")
    { "Tarea: ${it.title}, ID: ${it.id_task}," +
            " Fecha de vencimiento: ${it.due_date}," +
            " Completada: ${it.is_completed}" })

    println("Pruebas completadas.")


    taskRepository.updateTask(task)

    taskList = taskRepository.getTasksByTripId(21) // Cambia el ID del viaje según sea necesario
    println("Ejecutando pruebas de TaskRepository...")

    println(taskList .joinToString("\n")
    { "Tarea: ${it.title}, ID: ${it.id_task}," +
            " Fecha de vencimiento: ${it.due_date}," +
            " Completada: ${it.is_completed}" })

    println("Pruebas completadas.")
}