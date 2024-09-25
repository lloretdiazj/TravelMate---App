package com.pmdm.travelmate.data.services.lugar

import com.pmdm.travelmate.data.services.viaje.ViajeApi


data class LugarApi(
    val id: Int,
    val latitud: Float,
    val longitud: Float,
    val nombre: String,
    val viajeId: ViajeApi
)
