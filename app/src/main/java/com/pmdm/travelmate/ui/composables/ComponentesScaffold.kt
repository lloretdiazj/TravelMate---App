package com.pmdm.travelmate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pmdm.travelmate.ui.features.gasto.GastosEvent
import com.pmdm.travelmate.ui.features.inicio.InicioEvent
import com.pmdm.travelmate.ui.features.inicio.listaviaje.ListaViajesEvent
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraAplicacionAtrasSimple(
    comportamientoAnteScroll: TopAppBarScrollBehavior,
    esInicio: Boolean = false,
    onClickAtras: () -> Unit,
    titulo: String = ""
) = TopAppBar(
    modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .height(50.dp),
    title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 110.dp)
        ) {

            IconButton(onClick = onClickAtras) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
            Text(
                text = titulo,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
        }
    },
    scrollBehavior = comportamientoAnteScroll,
)


@Composable
fun NavBar(
    indexScreenState: Int,
    event: (InicioEvent) -> Unit
) {
    val titlesAndIcons = remember {
        listOf(
            "Inicio" to Icons.Filled.Add,
            "Viajes" to Icons.Filled.FormatListNumbered,
            "Gastos" to Icons.Filled.BarChart,
            "Ajustes" to Icons.Filled.VerifiedUser
        )
    }


    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .height(50.dp),
        contentColor = Color.Blue

    ) {
        titlesAndIcons.forEachIndexed { index, (title, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = title) },
                selected = indexScreenState == index,
                onClick = { event(InicioEvent.OnNavigationScreen(index)) },

                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraAppInferiorConSeleccion(
    event: (ListaViajesEvent) -> Unit,
    idSeleccionado: Int,
    onNavigateToUpdateViaje: (Int) -> Unit,
    comportamientoAnteScroll: BottomAppBarScrollBehavior
    = BottomAppBarDefaults.exitAlwaysScrollBehavior(),
    viajeState: ViajeUiState,
    inicioEvent: () -> Unit
) {
    val descripcionEIconos = remember {
        listOf(
            "Maps" to Icons.Filled.Map
        )
    }
    val context = LocalContext.current
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .height(50.dp),
        actions = {
            descripcionEIconos.forEach { (descripcion, icono) ->
                IconButton(
                    onClick = {
                        when (descripcion) {
                            "Maps" -> {
                                context.buscaEnMaps(viajeState.nombre)
                            }

                            "Editar" -> {
                                onNavigateToUpdateViaje(idSeleccionado)
                            }


                        }
                    }) {
                    Icon(
                        imageVector = icono,
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = descripcion
                    )
                }
            }
        },
        floatingActionButton = {

            IconButton(onClick = { event(ListaViajesEvent.onEliminaViaje(idSeleccionado)) }) {
                Icon(Icons.Filled.Delete, "Eliminar")
            }

        },
        scrollBehavior = comportamientoAnteScroll
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraCreaGasto(
    event: (GastosEvent) -> Unit,
    gastoState: GastoUiState,
    comportamientoAnteScroll: BottomAppBarScrollBehavior
    = BottomAppBarDefaults.exitAlwaysScrollBehavior(),
) {

    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .height(50.dp),
        actions = {
            Row(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.Start
            ) {
                TextField(
                    value = gastoState.descripcion,
                    onValueChange = { event(GastosEvent.onDescripcionGastoChanged(it)) },
                    placeholder = {
                        Text(
                            text = "Taxi",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.4f
                                )
                            )
                        )
                    },
                    modifier = Modifier.width(140.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.width(5.dp),
                    color = MaterialTheme.colorScheme.background
                )
                TextField(
                    value = gastoState.descripcion,
                    onValueChange = { event(GastosEvent.onGastoGastoChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(
                            text = "20,4",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.4f
                                )
                            )
                        )
                    },
                    modifier = Modifier.width(0.dp)
                )
            }
        },
        floatingActionButton = {
            IconButton(onClick = { event(GastosEvent.CreaGasto) }) {
                Icon(Icons.Filled.Add, "Crear")
            }


        },
        scrollBehavior = comportamientoAnteScroll
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraAplicacion(
    comportamientoAnteScroll: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    onInicioEvent: (InicioEvent) -> Unit,
    usuarioUiState: UsuarioInicioUiState,
    titulo: String = "",
    onNavegarAtras: () -> Unit
) = TopAppBar(
    modifier = Modifier.height(45.dp),
    title = {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(text = titulo, color = Color.White)
        }
    },
    actions = {
        IconButton(onClick = {
        }) {
            AccionesConMenuDesplegableSinSeleccion(
                snackbarHostState = snackbarHostState,
                usuarioUiState = usuarioUiState,
                onSeleccionarItem = onNavegarAtras
            )
        }
    },
    scrollBehavior = comportamientoAnteScroll,
    colors = TopAppBarColors(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primary,
    )
)


@Composable
fun AccionesConMenuDesplegableSinSeleccion(
    snackbarHostState: SnackbarHostState,
    usuarioUiState: UsuarioInicioUiState,
    onSeleccionarItem: () -> Unit
) {
    val descripcion = remember {
        listOf(
            "Cerrar Sesión"
        )
    }
    return AccionesConMenuDesplegable(
        itemsMenu = descripcion,
        snackbarHostState = snackbarHostState,
        usuarioUiState = usuarioUiState,
        onSeleccionarItem = onSeleccionarItem
    )
}

@Composable
fun AccionesConMenuDesplegable(
    itemsMenu: List<String>,
    snackbarHostState: SnackbarHostState,
    onSeleccionarItem: () -> Unit,
    usuarioUiState: UsuarioInicioUiState
) {
    if (itemsMenu.count() < 1)
        throw IllegalArgumentException("Se requieren al menos 1 item en el menú desplegable")

    var expandidoState by remember { mutableStateOf(false) }
    val cerrarMenu: () -> Unit = { expandidoState = false }

    IconButton(onClick = { expandidoState = true }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()

        ) {

            CircularImageFromPainterResource(
                imagen = usuarioUiState.imagen,
                contentDescription = "Imagen Usuario",
            )

        }
    }

    DropdownMenu(
        expanded = expandidoState,
        onDismissRequest = cerrarMenu
    ) {
        for (i in 0..<itemsMenu.count()) {
            DropdownMenuItem(
                text = { Text(itemsMenu[i]) },
                onClick = {

                    onSeleccionarItem()

                })
        }
    }
}