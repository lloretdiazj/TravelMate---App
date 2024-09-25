package com.pmdm.travelmate.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.lugar.muestralugares.MapaMuestraLugarScreen
import com.pmdm.travelmate.ui.features.lugar.muestralugares.MuestraLugaresViewModel

const val MuestraLugaresRoute = "muestraLugares"

fun NavGraphBuilder.mustraLugaresRoute(
    muestraLugaresViewModel: MuestraLugaresViewModel,
    onNavegarAtras: () -> Unit,
) {
    composable(route = MuestraLugaresRoute) {

        MapaMuestraLugarScreen(
            onClickAtras = onNavegarAtras,
            cameraPositionState = muestraLugaresViewModel.cameraPositionState,
            listaMarcadores = muestraLugaresViewModel.listaMarcadores
        )
    }
}

fun NavController.navigateToMuestraLugares() {
    this.navigate(MuestraLugaresRoute)

}
