package com.pmdm.travelmate.ui.features.gasto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinError
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinErrorNumerico
import com.pmdm.travelmate.ui.features.gasto.components.CardGasto
import com.pmdm.travelmate.ui.features.inicio.components.DialogoInformacion
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import java.util.Locale

@Composable
fun ContenidoGastos(
    listaGastos: List<GastoUiState>,
    onNavegarAtras: () -> Unit,
    paddingValues: PaddingValues,
    gastoState: GastoUiState,
    verDialogoEliminacion: Boolean,
    verDialogoCreacion: Boolean,
    gastosEvent: (GastosEvent) -> Unit

) {
    if (!listaGastos.isEmpty()) {
        Box {
            val maxChar = 7
            Column(modifier = Modifier.padding(paddingValues)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(5.dp)
                        .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        OutlinedTextFieldSinError(
                            textoState = gastoState.descripcion,
                            modifier = Modifier.width(150.dp),
                            onValueChange = {
                                gastosEvent(
                                    GastosEvent.onDescripcionGastoChanged(it.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.getDefault()
                                        ) else it.toString()
                                    })
                                )
                            })

                        HorizontalDivider(
                            modifier = Modifier.width(30.dp),
                            color = MaterialTheme.colorScheme.background
                        )

                        OutlinedTextFieldSinErrorNumerico(
                            textoState = if (gastoState.gasto != 0.0f)
                                gastoState.gasto.toString()
                            else "",
                            modifier = Modifier.width(90.dp),
                            onValueChange = {
                                gastosEvent(
                                    GastosEvent.onGastoGastoChanged(it)
                                )
                            })
                    }

                    FloatingActionButton(onClick = {
                        gastosEvent(
                            GastosEvent.CreaGasto
                        )
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
                        items = listaGastos,
                        key = { it.id }) { item ->
                        CardGasto(
                            gasto = item,
                            gastosEvent = gastosEvent,
                        )

                    }
                }
            }

            if (verDialogoCreacion) {
                DialogoInformacion(
                    onDismiss = { gastosEvent(GastosEvent.OnDissmissDialog) },
                    texto = "Gasto creado!"
                )
            }

            if (verDialogoEliminacion) {
                DialogoInformacion(
                    onDismiss = { gastosEvent(GastosEvent.OnDissmissDialog) },
                    texto = "Gasto Eliminado!"
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    OutlinedTextFieldSinError(
                        textoState = gastoState.descripcion,
                        modifier = Modifier.width(150.dp),
                        onValueChange = {
                            gastosEvent(
                                GastosEvent.onDescripcionGastoChanged(it.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.getDefault()
                                    ) else it.toString()
                                })
                            )
                        })

                    HorizontalDivider(
                        modifier = Modifier.width(30.dp),
                        color = MaterialTheme.colorScheme.background
                    )

                    OutlinedTextFieldSinErrorNumerico(
                        textoState = if (gastoState.gasto != 0.0f)
                            gastoState.gasto.toString()
                        else "",
                        modifier = Modifier.width(90.dp),
                        onValueChange = {
                            gastosEvent(
                                GastosEvent.onGastoGastoChanged(it)
                            )
                        })
                }

                FloatingActionButton(onClick = {
                    gastosEvent(
                        GastosEvent.CreaGasto
                    )
                }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Añadir gasto",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                "Aún no tienes gastos",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosScreen(
    listaGastos: List<GastoUiState>,
    gastoState: GastoUiState,
    onNavegarAtras: () -> Unit,
    verDialogoEliminacion: Boolean,
    verDialogoCreacion: Boolean,
    gastosEvent: (GastosEvent) -> Unit
) {
    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(

        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onNavegarAtras,
                titulo = "Gastos"
            )
        },
        content = {
            ContenidoGastos(
                listaGastos = listaGastos,
                onNavegarAtras = onNavegarAtras,
                gastosEvent = gastosEvent,
                gastoState = gastoState,
                paddingValues = it,
                verDialogoCreacion = verDialogoCreacion,
                verDialogoEliminacion = verDialogoEliminacion
            )
        })
}