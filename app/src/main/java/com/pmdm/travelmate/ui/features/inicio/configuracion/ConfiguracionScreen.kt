package com.pmdm.travelmate.ui.features.inicio.configuracion

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pmdm.travelmate.ui.features.inicio.InicioEvent
import com.pmdm.travelmate.ui.features.inicio.components.DialogoEliminaUsuario
import com.pmdm.travelmate.ui.features.inicio.components.DialogoSalir
import com.pmdm.travelmate.ui.features.inicio.components.DialogoUsuario
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState

@Composable
fun ConfiguracionScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    mostrarDialogoUsuario: Boolean,
    mostrarDialogoEliminacionUsuario: Boolean,
    usuarioInicioUiState: UsuarioInicioUiState,
    mostrarDialogoSalir: Boolean,
    onConfiguracionEvent: (ConfiguracionEvent) -> Unit,
    onClickAtras: () -> Unit,
    onClickSalir: () -> Unit,
    onNavigateToUpdateUser: (Int) -> Unit,
    onInicioEvent: (InicioEvent) -> Unit
) {
    var listaElementosCard = listOf<ElementoCardState>(
        ElementoCardState(0, "Usuario", Icons.Filled.Person),
        ElementoCardState(1, "Cambia tus Datos", Icons.Filled.Update),
        ElementoCardState(2, "Salir", Icons.AutoMirrored.Filled.ExitToApp),
        ElementoCardState(3, "Elimina tu Usuario", Icons.Filled.DeleteForever)
    )
    val activity = LocalContext.current as? Activity
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState()
            ) {

                items(
                    count = listaElementosCard.size,
                    key = { index -> listaElementosCard[index].id }
                ) {
                    CardElementos(
                        paddingValues = paddingValues,
                        elementosState = listaElementosCard[it],
                        onConfiguracionEvent = onConfiguracionEvent,
                        onNavigateToUpdateUser = onNavigateToUpdateUser,
                        onInicioEvent = onInicioEvent
                    )
                }
            }
        }
        if (mostrarDialogoUsuario)
            DialogoUsuario(
                usuarioInicioUiState = usuarioInicioUiState,
                onDismiss = { onConfiguracionEvent(ConfiguracionEvent.OnClickUsuario) }
            )

        if (mostrarDialogoEliminacionUsuario)
            DialogoEliminaUsuario(
                onConfiguracionEvent = onConfiguracionEvent,
                onClickAtras = onClickAtras
            )

        if (mostrarDialogoSalir)
            DialogoSalir(
                onConfiguracionEvent = onConfiguracionEvent
            ) {
                onClickSalir()
            }
    }

}


@Composable
fun CardElementos(
    paddingValues: PaddingValues,
    elementosState: ElementoCardState,
    onConfiguracionEvent: (ConfiguracionEvent) -> Unit,
    onNavigateToUpdateUser: (Int) -> Unit = {},
    onInicioEvent: (InicioEvent) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .height(250.dp)
            .clickable {
                when (elementosState.id) {
                    0 -> onConfiguracionEvent(ConfiguracionEvent.OnClickUsuario)
                    1 -> onInicioEvent(InicioEvent.OnUpdateUser(onNavigateToUpdateUser))
                    2 -> onConfiguracionEvent(ConfiguracionEvent.OnVerDialogoSalir)
                    3 -> onConfiguracionEvent(ConfiguracionEvent.OnClickEliminaUsuario)
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = elementosState.icono,
                contentDescription = elementosState.titulo
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = elementosState.titulo, textAlign = TextAlign.Center)
        }
    }
}