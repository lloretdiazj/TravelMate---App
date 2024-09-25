package com.pmdm.travelmate.ui.features.lugar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinError
import com.pmdm.travelmate.ui.features.inicio.components.DialogoInformacion
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.lugar.components.CardLugar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LugaresScreen(
    listaLugares: List<LugarUiState>,
    event: (LugaresEvent) -> Unit,
    onNavigateToNewLugar: () -> Unit,
    onNavegarAtras: () -> Unit,
    lugarUiState: LugarUiState,
    verDialogoCreacion: Boolean,
    verDialogoEliminacion: Boolean
) {

    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToNewLugar) {
                Icon(imageVector = Icons.Filled.Map, contentDescription = "Añade Lugar")
            }
        },
        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onNavegarAtras,
                titulo = "Lugares"
            )
        },
        content = {
            ContenidoLugares(
                paddingValues = it,
                listaLugares = listaLugares,
                lugarEvent = event,
                lugarUiState = lugarUiState,
                verDialogoCreacion = verDialogoCreacion,
                verDialogoEliminacion = verDialogoEliminacion
            )
        })

}

@Composable
fun ContenidoLugares(
    paddingValues: PaddingValues,
    lugarUiState: LugarUiState,
    listaLugares: List<LugarUiState>,
    lugarEvent: (LugaresEvent) -> Unit,
    verDialogoCreacion: Boolean,
    verDialogoEliminacion: Boolean
) {
    if (!listaLugares.isEmpty()) {
        Box {
            Column(modifier = Modifier.padding(paddingValues)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(10.dp)

                ) {
                    OutlinedTextFieldSinError(
                        textoState = lugarUiState.nombre,
                        modifier = Modifier.width(270.dp),
                        onValueChange = {
                            lugarEvent(LugaresEvent.OnCambioNombre(it.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }))
                        })

                    HorizontalDivider(
                        modifier = Modifier.width(15.dp),
                        color = MaterialTheme.colorScheme.background
                    )

                    FloatingActionButton(onClick = {
                        lugarEvent(LugaresEvent.CreaLugar)
                    }) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Añadir gasto",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }


                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(
                        items = listaLugares,
                        key = { it.id }) { item ->
                        CardLugar(lugarUiState = item, lugarEvent = lugarEvent)

                    }
                }
            }

            if (verDialogoCreacion) {
                DialogoInformacion(
                    onDismiss = { lugarEvent(LugaresEvent.OnDissmiss) },
                    texto = "Lugar creado!"
                )
            }

            if (verDialogoEliminacion) {
                DialogoInformacion(
                    onDismiss = { lugarEvent(LugaresEvent.OnDissmiss) },
                    texto = "Lugar Eliminado!"
                )
            }
        }
    } else {
        // Si no hay gastoPorViaje Informamos al usuario de que no hay gastos
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(10.dp)

            ) {
                OutlinedTextFieldSinError(
                    textoState = lugarUiState.nombre,
                    modifier = Modifier.width(270.dp),
                    onValueChange = {
                        lugarEvent(LugaresEvent.OnCambioNombre(it.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }))
                    })

                HorizontalDivider(
                    modifier = Modifier.width(15.dp),
                    color = MaterialTheme.colorScheme.background
                )

                FloatingActionButton(onClick = {
                    lugarEvent(LugaresEvent.CreaLugar)
                }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Añadir gasto",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }


            }
            Text(
                "Aún no tienes lugares",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                maxLines = 1
            )
        }
    }
}



