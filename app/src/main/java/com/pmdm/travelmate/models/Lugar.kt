package com.pmdm.travelmate.models

import com.pmdm.travelmate.models.Viaje


data class Lugar(
    val id: Int,
    val latitud: Float,
    val longitud: Float,
    val nombre: String,
    val viajeId: Viaje
)

