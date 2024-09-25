package com.pmdm.travelmate.ui.features.lugar.muestralugares

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaMuestraLugarScreen(
    onClickAtras: () -> Unit,
    listaMarcadores: List<SitioState>,
    cameraPositionState: CameraPositionState
) {
    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onClickAtras
            )
        },
        content = {
            ContentMapa(
                paddingValues = it,
                listaMarcadores = listaMarcadores,
                cameraPositionState = cameraPositionState,
            )
        }
    )
}

@Composable
fun ContentMapa(
    //Valores del Scaffold
    paddingValues: PaddingValues,
    //Lista de Marcadores
    listaMarcadores: List<SitioState>,
    // Posición cámara Maps
    cameraPositionState: CameraPositionState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Llamamos a la función
        GoogleMap(
            modifier = Modifier
                //Ocupa el máximo posible
                .fillMaxSize()
                .clickable {},
            cameraPositionState = cameraPositionState,
            onMapClick = {}
        ) {
            //Recorremos la lista de marcadores
            // Si son 0.0 se excluyen
            listaMarcadores.filter { it.latitud != 0.0 && it.longitud != 0.0 }
                .forEach {
                    Marker(
                        state = MarkerState(LatLng(it.latitud, it.longitud)),
                        title = it.nombre

                    )
                }
        }
    }
}

