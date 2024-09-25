package com.pmdm.travelmate.models

data class Usuario(
    val id: Int? = null,
    val email: String,
    val nombre: String,
    val contrasena: String,
    val imagen: String? = null
)
