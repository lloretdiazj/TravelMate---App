package com.pmdm.travelmate.ui.features.inicio.creaviaje

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api

sealed interface CreaViajeEvent {

    data class OnNombreViajeChange(val nombre: String) : CreaViajeEvent
    data class OnFechaInicioChange @OptIn(ExperimentalMaterial3Api::class) constructor(val fechaInicio: DatePickerState?) :
        CreaViajeEvent

    data class OnFechaFinChange @OptIn(ExperimentalMaterial3Api::class) constructor(val fechaFin: DatePickerState?) :
        CreaViajeEvent

    data class OnKilometrosRealizadosEvent(val kilometros: String) : CreaViajeEvent
    object OnClickFechaInicio : CreaViajeEvent
    object OnClickFechaFin : CreaViajeEvent
    object OnClickCrearViaje : CreaViajeEvent
    object OnDissmisFecha : CreaViajeEvent
    object OnDissmis : CreaViajeEvent
    object OnEliminaImagen : CreaViajeEvent
}