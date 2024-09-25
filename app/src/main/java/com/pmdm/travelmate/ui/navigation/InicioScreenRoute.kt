package com.pmdm.travelmate.ui.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.inicio.InicioScreen
import com.pmdm.travelmate.ui.features.inicio.InicioViewModel

const val InicioRoute = "inicio?{valorId}"

@RequiresApi(Build.VERSION_CODES.R)
fun NavGraphBuilder.inicioScreenRoute(
    inicioViewModel: InicioViewModel,
    onNavegarAtras: () -> Unit,
    onNavigateToUpdateUser: (Int) -> Unit,
    onNavigateToUpdateViaje: (Int) -> Unit
) {

    composable(route = InicioRoute) { backStackEntry ->
        val activity = LocalContext.current as? Activity
        InicioScreen(
            usuarioState = inicioViewModel.usuarioState,
            indiceState = inicioViewModel.indiceState,
            viajeUiState = inicioViewModel.viajeState,
            listaViajes = inicioViewModel.listaViajesState,
            listaGastos = inicioViewModel.listaGastosState,
            listaLugares = inicioViewModel.listaLugaresState,
            fechaInicio = inicioViewModel.fechaInicioState,
            fechaFin = inicioViewModel.fechaFinState,
            onCreaViajeEvent = inicioViewModel::onCreaViajeEvent,
            onInicioEvent = inicioViewModel::onInicioEvent,
            onNavegarAtras = onNavegarAtras,
            verDialogoFechaInicio = inicioViewModel.verDialogoFechaInicio,
            verDialogoFechaFin = inicioViewModel.verDialogoFechaFin,
            esItemSeleccionado = inicioViewModel.esItemSeleccionado,
            onListaViajesEvent = inicioViewModel::onListaViajes,
            idSeleccionado = inicioViewModel.idSeleccionado,
            onFotoCambiadaViaje = inicioViewModel::onFotoCambiada,
            onConfiguracionEvent = inicioViewModel::onConfiguracionEvent,
            verDialogoUsuario = inicioViewModel.verDialogoUsuario,
            mostrarDialogoEliminacionUsuario = inicioViewModel.verDialogoEliminacionUsuario,
            mostrarDialogoSalir = inicioViewModel.verDialogoSalirApp,
            mostrarDialogoInformacion = inicioViewModel.verDialogoViajeCreado,
            onNavigateToUpdateUser = onNavigateToUpdateUser,
            onNavigateToUpdateViaje = onNavigateToUpdateViaje,
            viajeStateMaps = inicioViewModel.viajeStateMaps,
            esViajeEliminado = inicioViewModel.verDialogoViajeEliminado,
            onClickSalir = {
                activity?.finish()
            }

        )
    }
}

fun NavController.navigateToInicio(valorId: String, navOptions: NavOptions? = null) {
    val x = "$InicioRoute/$valorId"
    this.navigate(x, navOptions)
}
