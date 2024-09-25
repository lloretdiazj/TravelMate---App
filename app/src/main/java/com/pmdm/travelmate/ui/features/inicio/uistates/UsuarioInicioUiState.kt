package com.pmdm.travelmate.ui.features.inicio.uistates

data class UsuarioInicioUiState(
    val id: Int? = 0,
    val email: String = "",
    val nombre: String = "",
    val contrasena: String = "",
    val imagen: String? = null
)
