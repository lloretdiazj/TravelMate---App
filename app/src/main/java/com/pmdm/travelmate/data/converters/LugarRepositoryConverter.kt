package com.pmdm.travelmate.data.converters

import com.pmdm.travelmate.models.Lugar
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.data.services.lugar.LugarApi


//region lugarEntity
fun LugarApi.toLugar(): Lugar =
    Lugar(
        id = this.id,
        latitud = this.latitud,
        longitud = this.longitud ,
        nombre = this.nombre,
        viajeId = this.viajeId.toViaje(),
    )

fun Lugar.toLugarApi(): LugarApi =
    LugarApi(
        id = this.id,
        latitud = this.latitud,
        longitud = this.longitud ,
        nombre = this.nombre,
        viajeId = this.viajeId.toViajeApi(),
    )

fun Lugar.toLugarUiState(): LugarUiState =
    LugarUiState(
        id = this.id,
        latitud = this.latitud,
        longitud = this.longitud ,
        nombre = this.nombre,
        viajeId = this.viajeId.toViajeUiState(),
    )

fun LugarUiState.toLugar(): Lugar =
    Lugar(
        id = this.id,
        latitud = this.latitud,
        longitud = this.longitud ,
        nombre = this.nombre,
        viajeId = this.viajeId.toViaje(),
    )



//endregion
