package com.pmdm.travelmate.ui.features.inicio.uistates


data class LugarUiState(
    val id: Int = 0,
    val latitud: Float = 0f,
    val longitud: Float = 0f,
    val nombre: String = "",
    val viajeId: ViajeUiState = ViajeUiState(),
)

