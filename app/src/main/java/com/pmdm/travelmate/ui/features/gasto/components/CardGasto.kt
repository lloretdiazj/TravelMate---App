package com.pmdm.travelmate.ui.features.gasto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.features.gasto.GastosEvent
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import com.pmdm.travelmate.ui.theme.TravelMateTheme

@Composable
fun CardGasto(
    gasto: GastoUiState,
    gastosEvent: (GastosEvent) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
        {

            val maxCharacters = 7 // Define el límite de caracteres que deseas mostrar
            val truncatedText = if (gasto.descripcion.length > maxCharacters) {
                "${gasto.descripcion.substring(0, maxCharacters)}..."
            } else {
                gasto.descripcion
            }
            Text(text = truncatedText, color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
            HorizontalDivider(
                modifier = Modifier.width(70.dp),
                color = MaterialTheme.colorScheme.background
            )
            Text(
                text = "${gasto.gasto}€",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }

        IconButton(onClick = { gastosEvent(GastosEvent.EliminaGasto(gasto.id)) }) {
            Icon(
                Icons.Default.Remove,
                contentDescription = "Eliminar gasto",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }

}

@Preview
@Composable
fun CardGastoPreview() {
    TravelMateTheme {
        CardGasto(
            gastosEvent = {},
            gasto = GastoUiState(
                id = 1,
                descripcion = "Gasto 1",
                gasto = 100.0f,
                viajeId = ViajeUiState(1, "Viaje 1", "Descripción viaje 1")
            )
        )
    }
}