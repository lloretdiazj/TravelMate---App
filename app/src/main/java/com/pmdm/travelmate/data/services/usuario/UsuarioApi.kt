package com.pmdm.travelmate.data.services.usuario


data class UsuarioApi(
    val id: Int? = 0,
    val email: String,
    val nombre: String,
    val contrasena: String,
    val imagen: String? = null
)

