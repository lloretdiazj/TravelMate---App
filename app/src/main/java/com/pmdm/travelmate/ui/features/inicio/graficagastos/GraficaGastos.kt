package com.pmdm.travelmate.ui.features.inicio.graficagastos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.features.inicio.graficagastos.composables.LeyendaComposable
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import io.jetchart.bar.Bar
import io.jetchart.bar.BarChart
import io.jetchart.bar.Bars
import io.jetchart.bar.renderer.label.SimpleBarLabelDrawer
import io.jetchart.bar.renderer.label.SimpleBarValueDrawer
import io.jetchart.bar.renderer.xaxis.BarXAxisDrawer
import io.jetchart.bar.renderer.yaxis.BarYAxisWithValueDrawer
import io.jetchart.common.animation.fadeInAnimation

@Composable
fun BarChartComposable(
    gastos: List<GastoUiState>, // Nos llega una lista de gastos
    listaViajes: List<ViajeUiState>,// Nos llega una lista
    paddingValues: PaddingValues
) {
    // Obtenemos los gastos por viaje
    val gastosPorViaje = gastos.groupBy { it.viajeId.id }
    // De cada gasto por viaje obtenemos sus porcentajes
    val porcentajes = gastosPorViaje.map { (nombreViaje, gastosDelViaje) ->
        val gastoDelViaje = gastosDelViaje.sumOf { it.gasto.toDouble() }
        nombreViaje to gastoDelViaje
    }.toMap()

    // Creamos una variable de tipo Bar que son los elementos necesarios para implementar la barra
    val bars = remember {
        porcentajes.map { (id, gastoDelViaje) ->
            val maxCharacters = 10 // Define el límite de caracteres que deseas mostrar
            //Del primer resultado del id que coincida cogemos su nombre segun su tamaño
            val truncatedText =
                if (listaViajes.filter { id == it.id }.first().nombre.length > maxCharacters) {
                    "${
                        listaViajes.filter { id == it.id }.first().nombre.substring(
                            0,
                            maxCharacters
                        )
                    }..."
                } else {
                    listaViajes.filter { id == it.id }.first().nombre
                }

            val gastoMaximo = listaViajes.filter { id == it.id }.first().kilometrosRealizados

            // Creamos los Elementos Bar
            Bar(
                label = truncatedText,
                value = gastoDelViaje.toFloat(),
                color = if (gastoDelViaje >= gastoMaximo && gastoMaximo != 0)
                    Color(0xFFFD7855.toInt())
                else Color(0xFF66CD54.toInt())
            )
        }
    }

    // Ancho Barra
    val width = 80 * bars.size

    // Si gastos hay gastos por viaje
    if (gastosPorViaje.isNotEmpty()) {

        Column(
            modifier = Modifier.padding(
                vertical = paddingValues.calculateTopPadding(),
                horizontal = 10.dp
            ),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            LeyendaComposable()
            //Creamos una columna
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(
                        top = 5.dp,
                        start = 5.dp,
                        end = 5.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .height(600.dp)
            ) {

                //Dejamos un poco de espacio
//             Spacer(modifier = Modifier.size(20.dp))
                BarChart(
                    // Creamos la gráfica con las barra obtenidas

                    bars = Bars(
                        bars = bars,
                        startAtZero = true
                    ),
                    //Scroll horizontal
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .width(width.dp)
                        .height(500.dp),
                    // 3s de animación al aparecer
                    animation = fadeInAnimation(3000),
                    //Color x
                    xAxisDrawer = BarXAxisDrawer(
                        axisLineColor = MaterialTheme.colorScheme.primary
                    ),
                    // Color y
                    yAxisDrawer = BarYAxisWithValueDrawer(
                        axisLineColor = MaterialTheme.colorScheme.primary,
                        labelTextColor = MaterialTheme.colorScheme.background
                    ),
                    labelDrawer = SimpleBarLabelDrawer(
                        labelTextColor = MaterialTheme.colorScheme.primary
                    ),
                    // Damos formato al texto de las barras
                    valueDrawer = SimpleBarValueDrawer(
                        drawLocation = SimpleBarValueDrawer.ValueDrawLocation.Inside,
                        valueTextColor = Color.Black,
                        formatter = {
                            "${it}€"
                        }
                    ),
                    // Margen entre barras
                    barHorizontalMargin = 5.dp
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