package com.pmdm.travelmate.ui.features.newuser.newuserpassword


import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptions
import com.pmdm.travelmate.ui.composables.CircularImageFromResourceClickable
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldEmail
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldPassword
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldWithErrorState


@Composable
fun NuevoUsuarioPassword(
    esNUevoUsuario: Boolean,
    newUserPasswordUiState: LoginPasswordUiState,
    validadorNewUserUiState: ValidacionLoginPasswordUiState,
    innerPaddingValues: PaddingValues,
    newUserPasswordEvent: (NewUserPasswordEvent) -> Unit,
    onNavigateToLogin: ((correo: String, navOptions: NavOptions?) -> Unit)?,
    onMostarSnackBar: () -> Unit,
    onFotoCambiada: (ImageBitmap) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            CircularImageFromResourceClickable(
                idImageResource = newUserPasswordUiState.imagen,
                contentDescription = "Logearse",
                onFotoCambiada = onFotoCambiada
            )
            TextButton(onClick = { newUserPasswordEvent(NewUserPasswordEvent.OnEliminaImagen) }) {
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
                textoState = newUserPasswordUiState.nombre,
                validacionState = validadorNewUserUiState.validacionNombre,
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Filled.VerifiedUser),
                        contentDescription = "Nombre"
                    )
                },
                onValueChange = { newUserPasswordEvent(NewUserPasswordEvent.NombreChanged(it)) },
            )

            OutlinedTextFieldEmail(
                modifier = Modifier.fillMaxWidth(),
                enabled = esNUevoUsuario,
                label = "Login",
                emailState = newUserPasswordUiState.email,
                validacionState = validadorNewUserUiState.validacionLogin,
                onValueChange = { newUserPasswordEvent(NewUserPasswordEvent.LoginChanged(it)) }
            )

            OutlinedTextFieldPassword(
                modifier = Modifier.fillMaxWidth(),
                label = "Password",
                passwordState = newUserPasswordUiState.contrasena,
                validacionState = validadorNewUserUiState.validacionPassword,
                onValueChange = { newUserPasswordEvent(NewUserPasswordEvent.PasswordChanged(it)) }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(

                onClick = {
                    newUserPasswordEvent(NewUserPasswordEvent.onClickCrearUsuario(onNavigateToLogin))
                    onMostarSnackBar()
                },
                modifier = Modifier
                    .width(200.dp)
            ) {
                Text("Crear cuenta")

            }
        }

    }
}

