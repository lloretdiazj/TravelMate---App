package com.pmdm.travelmate.ui.features.inicio.components

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pmdm.travelmate.ui.composables.CircularImageFromPainterResource
import com.pmdm.travelmate.ui.composables.OutlinedTextFieldSinError
import com.pmdm.travelmate.ui.composables.TextWithLine
import com.pmdm.travelmate.ui.features.inicio.configuracion.ConfiguracionEvent
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoFecha(
    onCreaViajeEvent: (DatePickerState) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        confirmButton = {
            TextButton(onClick = { onCreaViajeEvent(datePickerState) }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        content = {
            Column {

                DatePicker(
                    state = datePickerState
                )
            }
        },
        onDismissRequest = onDismiss
    )
}


@Composable
fun DialogoUsuario(
    usuarioInicioUiState: UsuarioInicioUiState,
    onDismiss: () -> Unit
) =
    AlertDialog(
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextWithLine(
                    texto = "Usuario", color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(20.dp))
                CircularImageFromPainterResource(
                    imagen = usuarioInicioUiState.imagen,
                    contentDescription = "Imagen usuario"
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextFieldSinError(
                    textoState = usuarioInicioUiState.nombre,
                    onValueChange = { },
                    readOnly = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center)

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextFieldSinError(
                    textoState = usuarioInicioUiState.email,
                    onValueChange = { },
                    readOnly = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        }
    )


@Composable
fun DialogoInformacion(
    onDismiss: () -> Unit,
    texto: String
) =
    AlertDialog(
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = texto,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Ok")
            }
        }
    )

@Composable
fun DialogoEliminaUsuario(
    onConfiguracionEvent: (ConfiguracionEvent) -> Unit,
    onClickAtras: () -> Unit,
) =
    AlertDialog(
        dismissButton = {
            TextButton(
                onClick = {
                    onConfiguracionEvent(ConfiguracionEvent.OnClickEliminaUsuario)
                }
            ) {
                Text("Cancelar")
            }

        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿Quieres Eliminar Usuario?",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        },
        onDismissRequest = { onConfiguracionEvent(ConfiguracionEvent.OnClickEliminaUsuario) },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfiguracionEvent(ConfiguracionEvent.OnEliminaUsuarioEvent)
                    onConfiguracionEvent(ConfiguracionEvent.OnClickEliminaUsuario)
                    onClickAtras()
                }
            ) {
                Text("Ok", color = MaterialTheme.colorScheme.error)
            }
        }
    )

@Composable
fun DialogoSalir(
    onConfiguracionEvent: (ConfiguracionEvent) -> Unit,
    onClickSalir: () -> Unit
) =
    AlertDialog(
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿Seguro que quieres Salir?",
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            onConfiguracionEvent(ConfiguracionEvent.OnVerDialogoSalir)
                        }
                    ) {
                        Text("Cancelar")
                    }

                    TextButton(
                        onClick = {
                            onClickSalir()


                        }
                    ) {
                        Text("Ok", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        },
        onDismissRequest = { onConfiguracionEvent(ConfiguracionEvent.OnVerDialogoSalir) },
        confirmButton = { }
    )