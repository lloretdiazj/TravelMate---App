package com.pmdm.travelmate.data.converters

import com.pmdm.travelmate.data.services.gasto.GastoApi
import com.pmdm.travelmate.models.Gasto
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState


//region gastoEntity
fun GastoApi.toGasto(): Gasto =
    Gasto(
        id = this.id,
        descripcion = this.descripcion,
        gasto = this.gasto,
        viajeId = this.viajeId.toViaje(),
    )

fun Gasto.toGastoApi(): GastoApi =
    GastoApi(
        id = this.id,
        descripcion = this.descripcion,
        gasto = this.gasto,
        viajeId = this.viajeId.toViajeApi(),
    )

fun Gasto.toGastosUiState(): GastoUiState =
    GastoUiState(
        id = this.id,
        descripcion = this.descripcion,
        gasto = this.gasto,
        viajeId = this.viajeId.toViajeUiState(),
    )

fun GastoUiState.toGastos(): Gasto =
    Gasto(
        id = this.id,
        descripcion = this.descripcion,
        gasto = this.gasto,
        viajeId = this.viajeId.toViaje(),
    )


//endregion