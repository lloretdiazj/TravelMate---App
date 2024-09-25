package com.pmdm.travelmate.ui.features.lugar

sealed interface LugaresEvent {

    data class EliminaLugar(val id: Int) : LugaresEvent
    data class OnCambioNombre(val nombre: String) : LugaresEvent
    object CreaLugar : LugaresEvent
    object OnDissmiss : LugaresEvent

}