package com.danielpons.aplicacionviajes.data.model

import kotlinx.datetime.Instant

data class Document(
    val id_document: Int? = null,
    val id_trip: Int,
    val title: String,
    val file_url: String,
    val file_type: String? = null,
    val upload_date: Instant? = null,
    val uploaded_by: String? = null,
    val notes: String? = null,
    // Sync fields
    val is_dirty: Boolean = false,
    val last_modified: Instant? = null,
    val server_id: Int? = null
)
