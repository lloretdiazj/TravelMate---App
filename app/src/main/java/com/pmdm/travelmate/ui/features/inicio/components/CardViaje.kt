package com.pmdm.travelmate.ui.features.inicio.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.R
import com.pmdm.travelmate.models.Gasto
import com.pmdm.travelmate.models.Viaje
import com.pmdm.travelmate.ui.composables.FechaFin
import com.pmdm.travelmate.ui.composables.FechaInicio
import com.pmdm.travelmate.ui.composables.ImageFromResource
import com.pmdm.travelmate.ui.features.inicio.listaviaje.ListaViajesEvent
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import com.pmdm.travelmate.ui.theme.Purple40
import com.pmdm.travelmate.ui.theme.TravelMateTheme
import com.pmdm.travelmate.utilities.images.Imagenes

@Composable
fun CardViaje(
    viaje: ViajeUiState,
    onListaViajesEvent: (ListaViajesEvent) -> Unit,
    listaLugares: List<LugarUiState>,
    listaGasto: List<GastoUiState>,
) {
    var cantidadGastos by remember {
        mutableStateOf(listaGasto.filter { viaje.id == it.viajeId.id }.size)
    }
    var gastoActual by remember {
        mutableStateOf(
            listaGasto.filter { viaje.id == it.viajeId.id }.sumOf { it.gasto.toDouble() })
    }
    var cantidadLugares by remember {
        mutableStateOf(listaLugares.filter { viaje.id == it.viajeId.id }.size)
    }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .padding(8.dp) // Añade un padding al Box para separarlo de los otros items
            .clip(RoundedCornerShape(8.dp)) // Hace que las esquinas del Box sean redondeadas
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onListaViajesEvent(ListaViajesEvent.onItemSeleccionado(viaje.id))
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(10.dp)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val painter = painterResource(R.drawable.viajedefecto)
            ImageFromResource(
                painter =
                if (viaje.imagen == null || viaje.imagen == "")
                    painter
                else
                    BitmapPainter(Imagenes.base64ToBitmap(viaje.imagen!!)),
                contentDescription = "imagenViaje"
            )

            HorizontalDivider(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colorScheme.background,
                thickness = 0.dp
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                    .width(400.dp)
                    .padding(1.dp),
            ) {
                val maxCharacters = 16 // Define el límite de caracteres que deseas mostrar
                val truncatedText = if (viaje.nombre.length > maxCharacters) {
                    "${viaje.nombre.substring(0, maxCharacters)}..."
                } else {
                    viaje.nombre
                }

                Text(
                    text = truncatedText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Spacer(
                    modifier = Modifier.size(5.dp)
                )

                Column(
                ) {

                    FechaInicio(viaje = viaje)
                    FechaFin(viaje = viaje)
                }

                Text(
                    text = "Gast. Máx.: " + viaje.kilometrosRealizados.toString() + "€",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    color = if (gastoActual >= viaje.kilometrosRealizados && viaje.kilometrosRealizados != 0)
                        MaterialTheme.colorScheme.error
                    else Color.Black

                )

                Row(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                        .height(20.dp)
                        .width(100.dp), // Añade un padding al Box para separarlo de los otros items

                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = cantidadGastos.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Filled.MonetizationOn,
                            contentDescription = "cantidad gastos",
                            tint = if (gastoActual >= viaje.kilometrosRealizados && viaje.kilometrosRealizados != 0)
                                MaterialTheme.colorScheme.error
                            else Color(0xFF357C28.toInt()),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(
                        modifier = Modifier.size(10.dp)
                    )
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = cantidadLugares.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "cantidad lugares",
                            modifier = Modifier.size(18.dp),
                            tint = Color.Red,
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.size(5.dp)
                )
            }
        }
    }
}
