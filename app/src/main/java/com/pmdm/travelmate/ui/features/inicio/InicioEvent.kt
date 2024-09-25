package com.pmdm.travelmate.ui.features.inicio


sealed interface InicioEvent {
    data class OnNavigationScreen(val index: Int) : InicioEvent
    data class OnUpdateUser(val onNavigateToUpdateUser: (Int) -> Unit) : InicioEvent
}

