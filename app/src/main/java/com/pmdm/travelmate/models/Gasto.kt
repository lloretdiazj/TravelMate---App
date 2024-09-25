package com.pmdm.travelmate.models

data class Gasto(
    val id: Int,
    val descripcion: String,
    val gasto: Float,
    val viajeId: Viaje

)