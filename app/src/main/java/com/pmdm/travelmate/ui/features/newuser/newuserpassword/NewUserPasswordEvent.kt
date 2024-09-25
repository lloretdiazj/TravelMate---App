package com.pmdm.travelmate.ui.features.newuser.newuserpassword

import androidx.navigation.NavOptions

sealed interface NewUserPasswordEvent {
    data class LoginChanged(val login: String) : NewUserPasswordEvent
    data class PasswordChanged(val password: String) : NewUserPasswordEvent
    data class NombreChanged(val nombre: String) : NewUserPasswordEvent
    data class onClickCrearUsuario(
        val onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?
    ) : NewUserPasswordEvent

    object OnEliminaImagen : NewUserPasswordEvent
}
