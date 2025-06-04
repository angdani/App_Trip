package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val id_task: Int? = null, // Identificador único de la tarea (puede ser nulo si aún no se ha guardado en la base de datos)
    val id_trip: Int, // Identificador del viaje al que pertenece la tarea
    var title: String, // Título o nombre de la tarea
    var due_date: String? = null, // Fecha límite para completar la tarea (formato String por simplicidad)
    var is_completed: Boolean = false, // Indica si la tarea está completada
    val created_at: Instant? = null, // Fecha y hora de creación (opcional)
    val completed_at: Instant? = null, // Fecha y hora de finalización (opcional)
    // Campos para sincronización
    val is_dirty: Boolean = false, // Indica si la tarea tiene cambios no sincronizados
    val last_modified: Instant? = null, // Fecha y hora de la última modificación (opcional)
    val server_id: Int? = null // Identificador de la tarea en el servidor (opcional)
)