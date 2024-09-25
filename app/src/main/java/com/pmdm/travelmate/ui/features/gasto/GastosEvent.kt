package com.pmdm.travelmate.ui.features.gasto

sealed interface GastosEvent {
    data class onDescripcionGastoChanged(val descripcion: String) : GastosEvent
    data class onGastoGastoChanged(val gasto: String) : GastosEvent
    data class EliminaGasto(val id: Int) : GastosEvent
    object CreaGasto : GastosEvent
    object OnDissmissDialog : GastosEvent
}