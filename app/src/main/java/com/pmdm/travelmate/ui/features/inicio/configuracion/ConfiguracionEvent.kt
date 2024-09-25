package com.pmdm.travelmate.ui.features.inicio.configuracion

sealed interface ConfiguracionEvent {

    object OnEliminaUsuarioEvent : ConfiguracionEvent
    object OnClickUsuario : ConfiguracionEvent
    object OnClickEliminaUsuario : ConfiguracionEvent
    object OnVerDialogoSalir : ConfiguracionEvent

}