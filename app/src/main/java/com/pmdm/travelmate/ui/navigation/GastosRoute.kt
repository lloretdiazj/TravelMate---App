package com.pmdm.travelmate.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.gasto.GastosScreen
import com.pmdm.travelmate.ui.features.gasto.GastosViewModel

const val GastosRoute = "gastos"

fun NavGraphBuilder.gastosRoute(
    gastoViewModel: GastosViewModel,
    onNavegarAtras: () -> Unit,
) {
    composable(route = GastosRoute) {


        GastosScreen(
            listaGastos = gastoViewModel.listaGasto,
            gastosEvent = gastoViewModel::onGastoEvent,
            onNavegarAtras = onNavegarAtras,
            verDialogoCreacion = gastoViewModel.muestraDialogoCreacion,
            verDialogoEliminacion = gastoViewModel.muestraDialogoEliminacion,
            gastoState = gastoViewModel.gastoState
        )
    }

}

fun NavController.navigateToGastos() {
    this.navigate(GastosRoute)

}

