package com.pmdm.travelmate.ui.features.updateviaje

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api

sealed interface UpdateViajeEvent {

    data class OnNombreViajeChange(val nombre: String) : UpdateViajeEvent
    data class OnFechaInicioChange @OptIn(ExperimentalMaterial3Api::class) constructor(val fechaInicio: DatePickerState?) :
        UpdateViajeEvent

    data class OnFechaFinChange @OptIn(ExperimentalMaterial3Api::class) constructor(val fechaFin: DatePickerState?) :
        UpdateViajeEvent

    data class OnKilometrosRealizadosEvent(val kilometros: String) : UpdateViajeEvent
    object OnClickFechaInicio : UpdateViajeEvent
    object OnClickFechaFin : UpdateViajeEvent
    object OnClickUpdateViaje : UpdateViajeEvent
    object OnDissmisFecha : UpdateViajeEvent
    object OnDismiss : UpdateViajeEvent
    object OnEliminaImagen : UpdateViajeEvent
}