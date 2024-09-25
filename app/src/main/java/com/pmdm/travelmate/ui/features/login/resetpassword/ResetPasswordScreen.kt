package com.pmdm.travelmate.ui.features.login.resetpassword


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.pmdm.travelmate.R
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple
import com.pmdm.travelmate.ui.composables.CircularImageFromResource
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldPassword
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinError
import com.pmdm.travelmate.ui.composables.TextWithLine
import com.pmdm.travelmate.ui.features.login.ValidacionLoginUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    email: String = "",
    nuevaContraseña: String,
    codigoNuevo: String,
    mostrarSnack: Boolean,
    mensaje: String,
    esCodigoEnviado: Boolean,
    onClickAtras: () -> Unit,
    validacionLoginUiState: ValidacionLoginUiState,
    onResetPassworEvent: (ResetPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?
) {
    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val onMostrarSnack: () -> Unit = {

        scope.launch {

            if (mostrarSnack) {
                snackBarHostState.showSnackbar(
                    message = mensaje, duration = SnackbarDuration.Long
                )
            }
            Log.d("SnackBar", mostrarSnack.toString())
        }
    }

    Scaffold(modifier = Modifier.nestedScroll(comportamientoAnteScroll.nestedScrollConnection),
        // Ranura que emite la barra de aplicación
        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onClickAtras,
                titulo = "Contraseña",
            )
        },

        snackbarHost = { SnackbarHost(snackBarHostState) }, content = {
            Box(
                modifier = Modifier.padding(10.dp)
            ) {
                ContenidoResetPass(
                    email = email,
                    nuevaContraseña = nuevaContraseña,
                    codigoNuevo = codigoNuevo,
                    esCodigoEnviado = esCodigoEnviado,
                    innerPadding = it,
                    onMostrarSnack = onMostrarSnack,
                    onResetPassworEvent = onResetPassworEvent,
                    onNavigateToLogin = onNavigateToLogin,
                    validacionLoginUiState = validacionLoginUiState,
                )
                if (mostrarSnack) {
                    if (mensaje.isNotEmpty()) Snackbar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(text = mensaje)
                    }
                }
            }
        })

}

@Composable
fun ContenidoResetPass(
    email: String = "",
    nuevaContraseña: String,
    codigoNuevo: String,
    esCodigoEnviado: Boolean,
    innerPadding: PaddingValues,
    onMostrarSnack: () -> Unit,
    validacionLoginUiState: ValidacionLoginUiState,
    onResetPassworEvent: (ResetPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularImageFromResource(
            idImageResource = R.drawable.pass, contentDescription = "Pass"
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextWithLine(
            texto = "Cambio de Contraseña", color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextFieldSinError(
            modifier = Modifier.fillMaxWidth(),
            textoState = email,
            textoPista = "example@example.com",
        ) { onResetPassworEvent(ResetPasswordEvent.onEmailChanged(it)) }
        Spacer(modifier = Modifier.height(20.dp))
        Button(

            onClick = {
                onResetPassworEvent(ResetPasswordEvent.onClickEnviaCodigo)
                onMostrarSnack()
            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            Text("Envia codigo")

        }

        Spacer(modifier = Modifier.height(20.dp))
        if (esCodigoEnviado) {

            OutlinedTextFieldSinError(
                modifier = Modifier.fillMaxWidth(),
                textoState = codigoNuevo,
                textoPista = "Código",
            ) { onResetPassworEvent(ResetPasswordEvent.onCodigoChanged(it)) }
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldPassword(modifier = Modifier.fillMaxWidth(),
                label = "Contraseña",
                passwordState = nuevaContraseña,
                validacionState = validacionLoginUiState,
                onValueChange = { onResetPassworEvent(ResetPasswordEvent.onNewPasswordChanged(it)) })
            Spacer(modifier = Modifier.height(20.dp))
            Button(

                onClick = {
                    onResetPassworEvent(ResetPasswordEvent.onClickResetPassword(onNavigateToLogin))
                    onMostrarSnack()
                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text("Cambia Contraseña")

            }
        }
    }
}
