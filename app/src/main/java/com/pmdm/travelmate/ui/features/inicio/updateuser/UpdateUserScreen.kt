package com.pmdm.travelmate.ui.features.inicio.updateuser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.travelmate.ui.composables.BarraAplicacionAtrasSimple
import com.pmdm.travelmate.ui.composables.CircularImageFromResourceClickable
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldEmail
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldPassword
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldWithErrorState
import com.pmdm.travelmate.ui.features.inicio.components.DialogoInformacion
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.ui.features.newuser.ValidacionNewUserUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateUserScreen(
    usuarioInicioUiState: UsuarioInicioUiState,
    nuevaContraseñaState: String,
    validacionUpdate: ValidacionUpdateUsuario,
    updateEvent: (UpdateUserEvent) -> Unit,
    onFotoCambiada: (ImageBitmap) -> Unit,
    onNavegarAtras: () -> Unit,
    usuarioActualizado: Boolean
) {
    val comportamientoAnteScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            BarraAplicacionAtrasSimple(
                comportamientoAnteScroll = comportamientoAnteScroll,
                onClickAtras = onNavegarAtras,
                titulo = "Modifica Usuario"
            )
        },
        content = {
            ContenidoActualizaUsuario(
                usuarioInicioUiState = usuarioInicioUiState,
                nuevaContraseñaState = nuevaContraseñaState,
                paddingValues = it,
                updateEvent = updateEvent,
                validacionUpdate = validacionUpdate,
                onFotoCambiada = onFotoCambiada,
                usuarioActualizado = usuarioActualizado
            )
        }

    )
}

@Composable
fun ContenidoActualizaUsuario(
    usuarioInicioUiState: UsuarioInicioUiState,
    nuevaContraseñaState: String,
    paddingValues: PaddingValues,
    validacionUpdate: ValidacionUpdateUsuario,
    updateEvent: (UpdateUserEvent) -> Unit,
    onFotoCambiada: (ImageBitmap) -> Unit,
    usuarioActualizado: Boolean
) {
    Box(
        modifier = Modifier.padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            CircularImageFromResourceClickable(
                idImageResource = usuarioInicioUiState.imagen,
                contentDescription = "Logearse",
                onFotoCambiada = onFotoCambiada
            )

            TextButton(onClick = { updateEvent(UpdateUserEvent.OnEliminaImagen) }) {
                Text(
                    text = "Elimina Imagen",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextFieldWithErrorState(
                modifier = Modifier.fillMaxWidth(),
                label = "Nombre",
                textoPista = "Introduce tu nombre",
                textoState = usuarioInicioUiState.nombre,
                validacionState = validacionUpdate.validacionNombre,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Filled.VerifiedUser),
                        contentDescription = "Nombre"
                    )
                },
                onValueChange = { updateEvent(UpdateUserEvent.OnCambioNombre(it)) },
            )

            OutlinedTextFieldEmail(
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                label = "Login",
                emailState = usuarioInicioUiState.email,
                validacionState = validacionUpdate.validacionLogin,
                onValueChange = { updateEvent(UpdateUserEvent.OnCambioEmail(it)) }
            )

            OutlinedTextFieldPassword(
                modifier = Modifier.fillMaxWidth(),
                label = "Password",
                passwordState = nuevaContraseñaState,
                validacionState = ValidacionNewUserUiState(),
                onValueChange = { updateEvent(UpdateUserEvent.OnCambioPassword(it)) }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(

                onClick = {
                    updateEvent(UpdateUserEvent.OnClickActualizarUsuario)
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text("Modificar")

            }

        }

        if (usuarioActualizado) {
            DialogoInformacion(
                onDismiss = { updateEvent(UpdateUserEvent.OnDissmiss) },
                texto = "Usuario Actualizado!"
            )
        }
    }
}
