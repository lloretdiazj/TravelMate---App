package com.pmdm.travelmate.ui.features.login

data class LoginUiState(
    val id: Int?,
    val email: String,
    val nombre: String,
    val contrasena: String,
    val imagen: String? = null,

    val estaLogeado: Boolean
) {
    constructor() : this(
        id = 0,
        email = "",
        nombre = "",
        contrasena = "",
        estaLogeado = false
    )
}
