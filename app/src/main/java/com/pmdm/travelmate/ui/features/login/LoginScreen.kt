package com.pmdm.travelmate.ui.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.R
import com.pmdm.travelmate.ui.composables.CircularImageFromResource
import com.pmdm.travelmate.ui.composables.TextNewAccount
import com.pmdm.travelmate.ui.features.login.components.UsuarioPassword
import com.pmdm.travelmate.ui.theme.Purple40


@Composable
fun LoginScreen(
    usuarioUiState: LoginUiState,
    validacionLoginUiState: ValidacionLoginUiState,
    mostrarSnack: Boolean,
    onLoginEvent: (LoginEvent) -> Unit,
    onMostrarSnackBar: () -> Unit,
    onNavigateToInicio: (correo: String) -> Unit,
    onNavigateToNewUser: () -> Unit,
    onNavigateToUpdatePass: () -> Unit
) {

    var recordarmeState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background)

        ) {
            CircularImageFromResource(
                idImageResource = R.drawable.travelmate, contentDescription = "Imagen Login"
            )

            Spacer(modifier = Modifier.height(20.dp))
            UsuarioPassword(modifier = Modifier.fillMaxWidth(),
                loginState = usuarioUiState.email,
                passwordState = usuarioUiState.contrasena,
                validacionLogin = validacionLoginUiState.validacionLogin,
                validacionPassword = validacionLoginUiState.validacionPassword,
                recordarmeState = recordarmeState,
                onValueChangeLogin = {
                    onLoginEvent(LoginEvent.LoginChanged(it))
                },
                onValueChangePassword = {
                    onLoginEvent(LoginEvent.PasswordChanged(it))
                },
                onCheckedChanged = { recordarmeState = it },
                onClickLogearse = {
                    onLoginEvent(LoginEvent.OnClickLogearse(onNavigateToInicio))
                    onMostrarSnackBar()
                })
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                "Olvidaste Password?",
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = Purple40,
                modifier = Modifier.clickable { onNavigateToUpdatePass() }
            )

            Spacer(modifier = Modifier.padding(50.dp))

            TextNewAccount(onClick = { onNavigateToNewUser() })
        }
        if (mostrarSnack) {
            var mensaje = ""
            if (validacionLoginUiState.hayError) mensaje =
                validacionLoginUiState.mensajeError ?: ""
            else if (usuarioUiState.estaLogeado) mensaje =
                "Entrando a la APP con usuario ${usuarioUiState.email}"
            else mensaje = "Error, no existe el usuario o contraseña incorrecta"
            Snackbar(
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = mensaje)
            }
        }
    }
}
