package com.pmdm.travelmate.ui.features.updateviaje

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple
import com.pmdm.travelmate.ui.composables.CircularImageFromResourceClickableNuevoViaje
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinError
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinErrorFecha
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinErrorNumerico
import com.pmdm.travelmate.ui.features.inicio.components.DialogoFecha
import com.pmdm.travelmate.ui.features.inicio.components.DialogoInformacion
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViajeScreen(
    viajeUiState: ViajeUiState,
    onUpdateViaje: (UpdateViajeEvent) -> Unit,
    fechaInicioState: String,
    fechaFinState: String,
    verDialogoFechaInicio: Boolean,
    verDialogoFechaFin: Boolean,
    verDialogoInfo: Boolean,
    cantidadGastos: Int,
    cantidadLugares: Int,
    onFotoCambiada: (ImageBitmap) -> Unit,
    onNavigateToGastos: () -> Unit,
    onNavigateToLugares: () -> Unit,
    onClickAtras: () -> Unit
) {
    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onClickAtras,
                titulo = "Modifica Viaje"
            )
        },
        content = {
            ContenidoUpdateViaje(
                it,
                viajeUiState = viajeUiState,
                fechaInicioState = fechaInicioState,
                fechaFinState = fechaFinState,
                verDialogoFechaInicio = verDialogoFechaInicio,
                verDialogoFechaFin = verDialogoFechaFin,
                onFotoCambiada = onFotoCambiada,
                onUpdateViaje = onUpdateViaje,
                verDialogoInfo = verDialogoInfo,
                onNavigateToGastos = onNavigateToGastos,
                onNavigateToLugares = onNavigateToLugares,
                cantidadGastos = cantidadGastos,
                cantidadLugares = cantidadLugares
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoUpdateViaje(
    paddingValues: PaddingValues,
    viajeUiState: ViajeUiState,
    onUpdateViaje: (UpdateViajeEvent) -> Unit,
    fechaInicioState: String,
    fechaFinState: String,
    verDialogoFechaInicio: Boolean,
    verDialogoFechaFin: Boolean,
    verDialogoInfo: Boolean,
    cantidadGastos: Int,
    cantidadLugares: Int,
    onNavigateToGastos: () -> Unit,
    onNavigateToLugares: () -> Unit,
    onFotoCambiada: (ImageBitmap) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues),
    )

    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            CircularImageFromResourceClickableNuevoViaje(
                idImageResource = viajeUiState.imagen,
                contentDescription = "Nuevo Viaje",
                onFotoCambiada = onFotoCambiada
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = { onUpdateViaje(UpdateViajeEvent.OnEliminaImagen) }) {
                Text(
                    text = "Elimina Imagen",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp

                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextFieldSinError(
                modifier = Modifier.fillMaxWidth(),
                textoState = viajeUiState.nombre,
                textoPista = "Sevilla",
            ) {
                onUpdateViaje(UpdateViajeEvent.OnNombreViajeChange(it.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }))
            }
            Text(
                text = "*Procura poner una ciudad real para una mejor experiencia.",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldSinErrorFecha(

                    textoState = fechaInicioState,
                    textoPista = "15/06/2024",
                )

                Button(
                    onClick = { onUpdateViaje(UpdateViajeEvent.OnClickFechaInicio) },
                    modifier = Modifier.size(50.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        Icons.Filled.CalendarToday,
                        contentDescription = "Calendar",

                        )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextFieldSinErrorFecha(
                    textoState = fechaFinState,
                    textoPista = "20/06/2024",
                )

                Button(
                    onClick = { onUpdateViaje(UpdateViajeEvent.OnClickFechaFin) },
                    modifier = Modifier.size(50.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        Icons.Filled.CalendarToday,
                        contentDescription = "Calendar",
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldSinErrorNumerico(
                modifier = Modifier.fillMaxWidth(),
                textoState =
                if (viajeUiState.kilometrosRealizados != 0)
                    viajeUiState.kilometrosRealizados.toString()
                else "",
                textoPista = "Gasto Máximo Por Viaje (€)",
            ) { onUpdateViaje(UpdateViajeEvent.OnKilometrosRealizadosEvent(it)) }
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Button(
                    onClick = {
                        onNavigateToGastos()
                    },
                    modifier = Modifier
                ) {
                    Text("Gastos ($cantidadGastos)")
                }
                Button(
                    onClick = {
                        onNavigateToLugares()
                    },
                    modifier = Modifier
                ) {
                    Text("Lugares (${cantidadLugares})")
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    onUpdateViaje(UpdateViajeEvent.OnClickUpdateViaje)
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text("Modificar Viaje")
            }


        }
        if (verDialogoFechaInicio) {
            DialogoFecha(
                onCreaViajeEvent = { onUpdateViaje(UpdateViajeEvent.OnFechaInicioChange(it)) },
                onDismiss = { onUpdateViaje(UpdateViajeEvent.OnDissmisFecha) }
            )
        }

        if (verDialogoFechaFin) {
            DialogoFecha(
                onCreaViajeEvent = { onUpdateViaje(UpdateViajeEvent.OnFechaFinChange(it)) },
                onDismiss = { onUpdateViaje(UpdateViajeEvent.OnDissmisFecha) }
            )
        }

        if (verDialogoInfo) {
            DialogoInformacion(
                texto = "Viaje Modificado",
                onDismiss = { onUpdateViaje(UpdateViajeEvent.OnDismiss) }
            )
        }
    }

}
