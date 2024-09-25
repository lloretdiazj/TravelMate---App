package com.pmdm.travelmate.ui.features.inicio.uistates

data class GastoUiState(
    val id: Int = 0,
    val descripcion: String = "",
    val gasto: Float = 0.0f,
    val viajeId: ViajeUiState = ViajeUiState(),
)