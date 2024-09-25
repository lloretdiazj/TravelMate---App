package com.pmdm.travelmate.ui.features.inicio.updateuser

sealed interface UpdateUserEvent {

    data class OnCambioNombre(val nombre: String) : UpdateUserEvent
    data class OnCambioPassword(val password: String) : UpdateUserEvent
    data class OnCambioEmail(val email: String) : UpdateUserEvent
    object OnEliminaImagen : UpdateUserEvent
    object OnDissmiss : UpdateUserEvent
    object OnClickActualizarUsuario : UpdateUserEvent
}