package com.danielpons.travelapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TripDto(
    @SerialName("id_trip")
    var id: Int? = null, // id es opcional para permitir inserciones sin especificarlo
    @SerialName("name")
    val name: String,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null
) {
    // Constructor solo con el nombre (para inserciones)
    constructor(name: String) : this(null, name)

    // Constructor con el nombre y las fechas (para inserciones)
    constructor(name: String, startDate: String, endDate: String) : this(null, name, startDate, endDate)
}

