package com.pmdm.travelmate.data.converters

import com.pmdm.travelmate.data.services.viaje.ViajeApi
import com.pmdm.travelmate.models.Viaje
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import java.text.SimpleDateFormat
import java.util.Date


//region viajeEntity
fun ViajeApi.toViaje(): Viaje =
    Viaje(
        id = this.id,
        fechaFin = fechaFin.toString(),
        fechaInicio = fechaInicio.toString(),
        kilometrosRealizados = this.kilometrosRealizados,
        nombre = this.nombre,
        usuarioId = this.usuarioId.toUsuario(),
        imagen = this.imagen
    )


fun Viaje.toViajeApi(): ViajeApi =
    ViajeApi(
        id = this.id,
        fechaFin = this.fechaFin,
        fechaInicio = this.fechaInicio,
        kilometrosRealizados = this.kilometrosRealizados,
        nombre = this.nombre,
        usuarioId = this.usuarioId.toUsuarioApi(),
        imagen = this.imagen
    )

fun Viaje.toViajeUiState(): ViajeUiState =
    ViajeUiState(
        id = this.id,
        fechaFin = this.fechaFin,
        fechaInicio = this.fechaInicio,
        kilometrosRealizados = this.kilometrosRealizados,
        nombre = this.nombre,
        usuarioId = this.usuarioId.toUsuarioInicioUiState(),
        imagen = this.imagen
    )

fun ViajeUiState.toViaje(): Viaje =
    Viaje(
        id = this.id,
        fechaFin = this.fechaFin,
        fechaInicio = this.fechaInicio,
        kilometrosRealizados = this.kilometrosRealizados,
        nombre = this.nombre,
        usuarioId = this.usuarioId.toUsuario(),
        imagen = this.imagen
    )

fun stringToDate(str: String): Date? {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.parse(str)
}

fun dateToString(date: Date): String {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}


//endregion
