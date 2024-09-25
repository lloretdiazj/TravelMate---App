package com.pmdm.travelmate.ui.features.newuser.newuserpassword

data class LoginPasswordUiState(
    val email: String,
    val nombre: String,
    val contrasena: String,
    val imagen: String? = null
) {
    constructor() : this(
        email = "",
        nombre = "",
        contrasena = "",
    )
}