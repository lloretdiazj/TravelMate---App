package com.pmdm.travelmate.data.services.gasto

import com.pmdm.travelmate.data.services.viaje.ViajeApi


data class GastoApi(
    val id: Int,
    val descripcion: String,
    val gasto: Float,
    val viajeId: ViajeApi,
)

