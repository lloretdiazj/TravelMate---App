package com.pmdm.travelmate.ui.features.newuser

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple
import com.pmdm.travelmate.ui.features.newuser.newuserpassword.NewUserPasswordEvent
import com.pmdm.travelmate.ui.features.newuser.newuserpassword.NuevoUsuarioPassword
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewUserScreen(
    newUserUiState: NewUserUiState,
    validacionNewUserUiState: ValidacionNewUserUiState,
    esNuevoClienteState: Boolean,
    onClickAtras: () -> Unit,
    mostrarSnack: Boolean,
    mensaje: String,
    onNewUserPasswordEvent: (NewUserPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?,
    onFotoCambiada: (ImageBitmap) -> Unit
) {
    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val onMostrarSnack: () -> Unit = {

        scope.launch {

            if (mostrarSnack) {
                snackBarHostState.showSnackbar(
                    message = mensaje,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(comportamientoAnteScroll.nestedScrollConnection),
        // Ranura que emite la barra de aplicación
        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onClickAtras,
                titulo = "Registro"
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        // Ranura que emite el contenido principal qeu recibe el padding
        content = { innerPadding ->
            Box(
                modifier = Modifier.padding(10.dp)

            ) {
                NuevoUsuarioPassword(
                    esNUevoUsuario = esNuevoClienteState,
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState,
                    validadorNewUserUiState = validacionNewUserUiState.validacionLoginPasswordUiState,
                    newUserPasswordEvent = onNewUserPasswordEvent,
                    innerPaddingValues = innerPadding,
                    onNavigateToLogin = onNavigateToLogin,
                    onMostarSnackBar = onMostrarSnack,
                    onFotoCambiada = onFotoCambiada
                )

            }
        }
    )
}




