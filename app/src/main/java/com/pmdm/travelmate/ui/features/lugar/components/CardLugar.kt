package com.pmdm.travelmate.ui.features.lugar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.composables.buscaEnMaps
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import com.pmdm.travelmate.ui.features.lugar.LugaresEvent
import com.pmdm.travelmate.ui.theme.TravelMateTheme


@Composable
fun CardLugar(
    lugarUiState: LugarUiState,
    lugarEvent: (LugaresEvent) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        val maxCharacters = 15 // Define el límite de caracteres que deseas mostrar
        val truncatedText = if (lugarUiState.nombre.length > maxCharacters) {
            "${lugarUiState.nombre.substring(0, maxCharacters)}..."
        } else {
            lugarUiState.nombre
        }
        Text(text = truncatedText, color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)

        Row {
            IconButton(onClick = {
                context.buscaEnMaps("${lugarUiState.nombre} ${lugarUiState.viajeId.nombre}")
            }) {
                Icon(
                    Icons.Default.Map,
                    contentDescription = "Abre Lugar en Maps",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { lugarEvent(LugaresEvent.EliminaLugar(lugarUiState.id)) }) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Eliminar Lugar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardLugar() {

    TravelMateTheme {
        CardLugar(lugarUiState = LugarUiState(
            0,
            nombre = "Prueba lugar",
            latitud = 0f,
            longitud = 0f,
            viajeId = ViajeUiState()
        ),
            lugarEvent = {})
    }

}