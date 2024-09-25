package com.pmdm.travelmate.ui.features.inicio.listaviaje

sealed interface ListaViajesEvent {

    data class onEliminaViaje(val id: Int) : ListaViajesEvent
    data class onItemSeleccionado(val index: Int) : ListaViajesEvent
    object OnDissmiss : ListaViajesEvent
}
