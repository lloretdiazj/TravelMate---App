package com.pmdm.travelmate.ui.features.login.resetpassword

import androidx.navigation.NavOptions

sealed interface ResetPasswordEvent {
    data class onEmailChanged(val email: String) : ResetPasswordEvent
    data class onCodigoChanged(val codigo: String) : ResetPasswordEvent
    data class onNewPasswordChanged(val pass: String) : ResetPasswordEvent
    object onClickEnviaCodigo : ResetPasswordEvent
    data class onClickResetPassword(val onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?) :
        ResetPasswordEvent
}