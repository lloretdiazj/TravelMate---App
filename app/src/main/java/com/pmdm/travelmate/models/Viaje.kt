package com.pmdm.travelmate.models

import java.util.Date


data class Viaje(
    val id: Int,
    val fechaFin: String? = "",
    val fechaInicio: String? = "",
    val kilometrosRealizados: Int,
    val nombre: String,
    val usuarioId: Usuario,
    val imagen: String? = null

)

