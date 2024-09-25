package com.pmdm.travelmate.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.updateviaje.UpdateViajeScreen
import com.pmdm.travelmate.ui.features.updateviaje.UpdateViajeViewModel


const val UpdateViajeRoute = "updateViaje?{id}"

@RequiresApi(Build.VERSION_CODES.R)
fun NavGraphBuilder.updateViajeRoute(
    updateViewModel: UpdateViajeViewModel,
    onClickAtras: () -> Unit,
    onNavigateToGastos: () -> Unit,
    onNavigateToLugares: () -> Unit
) {
    composable(route = UpdateViajeRoute) {


        UpdateViajeScreen(
            viajeUiState = updateViewModel.viajeState,
            fechaInicioState = updateViewModel.fechaInicioState,
            fechaFinState = updateViewModel.fechaFinState,
            verDialogoFechaInicio = updateViewModel.verDialogoFechaInicio,
            verDialogoFechaFin = updateViewModel.verDialogoFechaFin,
            verDialogoInfo = updateViewModel.verDialogoCambioRealizado,
            onFotoCambiada = updateViewModel::onFotoCambiada,
            onUpdateViaje = updateViewModel::onViajeUpdateEvent,
            cantidadGastos = updateViewModel.cantidadGastos,
            cantidadLugares = updateViewModel.cantidadLugares,
            onNavigateToGastos = onNavigateToGastos,
            onNavigateToLugares = onNavigateToLugares,
            onClickAtras = onClickAtras
        )

    }

}

fun NavController.navigateToUpdateViaje(id: Int, navOptions: NavOptions? = null) {

    val x = "$UpdateViajeRoute/$id"
    this.navigate(x, navOptions)

}
