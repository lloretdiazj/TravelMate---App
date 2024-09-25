package com.pmdm.travelmate.ui.features.inicio.uistates


data class ViajeUiState(
    val id: Int = 0,
    val fechaFin: String? = "",
    val fechaInicio: String? = "",
    val kilometrosRealizados: Int = 0,
    val nombre: String = "",
    val usuarioId: UsuarioInicioUiState = UsuarioInicioUiState(),
    val imagen: String? = null
)

