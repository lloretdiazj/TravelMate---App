package com.pmdm.travelmate.ui.features.login

sealed interface LoginEvent {
    data class LoginChanged(val login: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data class OnClickLogearse(val onNavigateInicio: ((id: String) -> Unit)?) : LoginEvent
    data class OnClickNewUser(val onNavigateToNewUser: () -> Unit) : LoginEvent
    data class OnUpdatePassword(val onNavigateToUpdatePassword: () -> Unit) : LoginEvent
}
