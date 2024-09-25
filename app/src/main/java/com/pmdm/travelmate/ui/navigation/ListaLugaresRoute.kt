package com.pmdm.travelmate.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.lugar.LugarViewModel
import com.pmdm.travelmate.ui.features.lugar.LugaresScreen

const val LugaresRoute = "lugares"

fun NavGraphBuilder.lugarRoute(
    lugarViewModel: LugarViewModel,
    onNavegarAtras: () -> Unit,
    onNavigateToNewLugar: () -> Unit
) {
    composable(route = LugaresRoute) {

        LugaresScreen(
            listaLugares = lugarViewModel.listaLugares,
            event = lugarViewModel::onLugaresEvent,
            onNavegarAtras = onNavegarAtras,
            onNavigateToNewLugar = onNavigateToNewLugar,
            lugarUiState = lugarViewModel.lugarViewModel,
            verDialogoCreacion = lugarViewModel.verDialogoCreacion,
            verDialogoEliminacion = lugarViewModel.verDialogoEliminacion
        )
    }

}

fun NavController.navigateToLugares() {
    this.navigate(LugaresRoute)

}
