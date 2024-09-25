package com.pmdm.travelmate.ui.features.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.pmdm.travelmate.ui.composables.BarraAplicacion
import com.pmdm.travelmate.ui.composables.BarraAppInferiorConSeleccion
import com.pmdm.travelmate.ui.composables.NavBar
import com.pmdm.travelmate.ui.features.inicio.configuracion.ConfiguracionEvent
import com.pmdm.travelmate.ui.features.inicio.configuracion.ConfiguracionScreen
import com.pmdm.travelmate.ui.features.inicio.creaviaje.CreaViajeEvent
import com.pmdm.travelmate.ui.features.inicio.creaviaje.CreaViajeScreen
import com.pmdm.travelmate.ui.features.inicio.graficagastos.BarChartComposable
import com.pmdm.travelmate.ui.features.inicio.listaviaje.ListaViajesEvent
import com.pmdm.travelmate.ui.features.inicio.listaviaje.ListadoViajesScreen
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    usuarioState: UsuarioInicioUiState,
    indiceState: Int,
    viajeUiState: ViajeUiState,
    viajeStateMaps: ViajeUiState,
    listaViajes: List<ViajeUiState>,
    listaGastos: List<GastoUiState>,
    listaLugares: List<LugarUiState>,
    fechaInicio: String,
    fechaFin: String,
    verDialogoFechaInicio: Boolean,
    verDialogoFechaFin: Boolean,
    esItemSeleccionado: Boolean,
    idSeleccionado: Int,
    verDialogoUsuario: Boolean,
    mostrarDialogoEliminacionUsuario: Boolean,
    mostrarDialogoSalir: Boolean,
    mostrarDialogoInformacion: Boolean,
    onCreaViajeEvent: (CreaViajeEvent) -> Unit,
    onInicioEvent: (InicioEvent) -> Unit,
    onListaViajesEvent: (ListaViajesEvent) -> Unit,
    onNavegarAtras: () -> Unit,
    onFotoCambiadaViaje: (ImageBitmap) -> Unit,
    onConfiguracionEvent: (ConfiguracionEvent) -> Unit,
    onNavigateToUpdateUser: (Int) -> Unit,
    onNavigateToUpdateViaje: (Int) -> Unit,
    esViajeEliminado: Boolean,
    onClickSalir: () -> Unit,

    ) {

    val comportamientoAnteScroll = TopAppBarDefaults.pinnedScrollBehavior()
    var snackBarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Scaffold(floatingActionButton = {
            if (esItemSeleccionado) {
                FloatingActionButton(onClick = {
                    onNavigateToUpdateViaje(idSeleccionado)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar viaje",
                    )
                }
            }
        }, content = {
            when (indiceState) {

                0 -> {
                    CreaViajeScreen(
                        viajeUiState = viajeUiState,
                        onCreaViajeEvent = onCreaViajeEvent,
                        fechaInicioState = fechaInicio,
                        fechaFinState = fechaFin,
                        paddingValues = it,
                        verDialogoFechaInicio = verDialogoFechaInicio,
                        verDialogoFechaFin = verDialogoFechaFin,
                        verDialogoInformacion = mostrarDialogoInformacion,
                        onFotoCambiada = onFotoCambiadaViaje
                    )
                }

                1 -> {
                    ListadoViajesScreen(
                        viajes = listaViajes,
                        paddingValues = it,
                        onListaViajesEvent = onListaViajesEvent,
                        listaLugares = listaLugares,
                        listaGasto = listaGastos,
                        esViajeEliminado = esViajeEliminado

                    )
                }

                2 -> {
                    BarChartComposable(listaGastos, listaViajes, paddingValues = it)
                }

                3 -> {
                    ConfiguracionScreen(
                        paddingValues = it,
                        onConfiguracionEvent = onConfiguracionEvent,
                        onClickAtras = onNavegarAtras,
                        usuarioInicioUiState = usuarioState,
                        mostrarDialogoUsuario = verDialogoUsuario,
                        mostrarDialogoEliminacionUsuario = mostrarDialogoEliminacionUsuario,
                        mostrarDialogoSalir = mostrarDialogoSalir,
                        onClickSalir = onClickSalir,
                        onNavigateToUpdateUser = onNavigateToUpdateUser,
                        onInicioEvent = onInicioEvent
                    )


                }
            }
        }, topBar = {
            var titulo by remember {
                mutableStateOf("")
            }
            when (indiceState) {
                0 -> {
                    titulo = "Crear Viaje"
                }

                1 -> {
                    titulo = "Viajes"
                }

                2 -> {
                    titulo = "Gastos Por Viaje"
                }

                3 -> {
                    titulo = "Usuario"
                }
            }
            BarraAplicacion(
                comportamientoAnteScroll = comportamientoAnteScroll,
                snackbarHostState = snackBarHostState,
                onInicioEvent = onInicioEvent,
                usuarioUiState = usuarioState,
                onNavegarAtras = onNavegarAtras,
                titulo = titulo
            )

        },


            bottomBar = {
                if (!esItemSeleccionado) {
                    NavBar(
                        indexScreenState = indiceState, event = onInicioEvent
                    )
                } else {
                    BarraAppInferiorConSeleccion(
                        event = onListaViajesEvent,
                        inicioEvent = { onInicioEvent(InicioEvent.OnNavigationScreen(0)) },
                        idSeleccionado = idSeleccionado,
                        viajeState = viajeStateMaps,
                        onNavigateToUpdateViaje = onNavigateToUpdateViaje
                    )
                }
            })


    }

}