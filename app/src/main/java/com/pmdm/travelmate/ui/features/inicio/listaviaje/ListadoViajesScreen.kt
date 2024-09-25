package com.pmdm.travelmate.ui.features.inicio.listaviaje

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.features.inicio.components.CardViaje
import com.pmdm.travelmate.ui.features.inicio.components.DialogoInformacion
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState

@Composable
fun ListadoViajesScreen(
    viajes: List<ViajeUiState>,
    paddingValues: PaddingValues,
    listaLugares: List<LugarUiState>,
    listaGasto: List<GastoUiState>,
    esViajeEliminado: Boolean,
    onListaViajesEvent: (ListaViajesEvent) -> Unit,

    ) {
    if (!viajes.isEmpty()) {

        Box {

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(all = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(
                    items = viajes,
                    key = { it.id }) { item ->
                    CardViaje(
                        viaje = item,
                        onListaViajesEvent = onListaViajesEvent,
                        listaGasto = listaGasto,
                        listaLugares = listaLugares

                    )

                }
            }

            if (esViajeEliminado) {
                DialogoInformacion(
                    onDismiss = { onListaViajesEvent(ListaViajesEvent.OnDissmiss) },
                    texto = "Viaje eliminado!"
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

            Text(
                "Aún no tienes viajes",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                maxLines = 1
            )
        }
    }
}