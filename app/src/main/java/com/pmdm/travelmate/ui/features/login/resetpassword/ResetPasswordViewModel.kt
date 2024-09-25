package com.pmdm.travelmate.ui.features.login.resetpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.services.ApiServicesException
import com.pmdm.travelmate.models.Usuario
import com.pmdm.travelmate.ui.features.login.ValidacionLoginUiState
import com.pmdm.travelmate.ui.features.login.ValidadorLogin
import com.pmdm.travelmate.ui.navigation.HomeRoute
import com.pmdm.travelmate.utilities.cifrado.toSHA256
import com.pmdm.travelmate.utilities.email.Email
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorLogin
) : ViewModel() {

    var usuario by mutableStateOf(Usuario(0, "", "", ""))
    var emailIntroducido by mutableStateOf("")
    var email by mutableStateOf(Email)
    var codigo by mutableStateOf("")
    var codigoIntroducido by mutableStateOf("")
    var contraseñaIntroducida by mutableStateOf("")
    var mensajeSnack by mutableStateOf("")
    var mostrarSnack by mutableStateOf(false)
    var esCodigoEnviado by mutableStateOf(false)
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set


    fun onResetPasswordEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.onEmailChanged -> {
                emailIntroducido = event.email
            }

            is ResetPasswordEvent.onCodigoChanged -> {
                codigoIntroducido = event.codigo
            }

            is ResetPasswordEvent.onNewPasswordChanged -> {
                contraseñaIntroducida = event.pass
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionPassword = validadorLogin.validadorPassword.valida(event.pass)
                )
            }

            is ResetPasswordEvent.onClickEnviaCodigo -> {
                viewModelScope.launch {
                    try {
                        usuario = usuario.copy(
                            id = usuarioRepository.getByMail(emailIntroducido)!!.id,
                            email = usuarioRepository.getByMail(emailIntroducido)!!.email,
                            contrasena = usuarioRepository.getByMail(emailIntroducido)!!.contrasena,
                            nombre = usuarioRepository.getByMail(emailIntroducido)!!.nombre
                        )   // Contruimos un nuevo UsuarioUiState con la información recibida con su email.
                        delay(3000L)
                        if (usuario.email != "") {  // Si el mail está vacío envía correo.
                            codigo = email.sendEmailPassReset(usuario)
                            esCodigoEnviado = true // Confirmamos que se ha envíado el código.
                        } else { // Sino el mail introducido no existe
                            mensajeSnack = "El email introducido no existe."
                            mostrarSnack = true
                        }
                    } catch (e: ApiServicesException) { // Si hay algún error procedemos a mostrar la Snack con un mensaje
                        mensajeSnack = "Error al enviar el email con el código."
                        mostrarSnack = true
                    }
                }
            }

            is ResetPasswordEvent.onClickResetPassword -> {

                try {
                    if (codigoIntroducido == codigo) {
                        if (contraseñaIntroducida != usuario.contrasena) {
                            viewModelScope.launch {
                                usuarioRepository.update(usuario.copy(contrasena = contraseñaIntroducida.toSHA256()))
                                mensajeSnack = "Contraseña Cambiada Correctamente."
                                mostrarSnack = true
                            }
                        } else {
                            mensajeSnack = "No puede usar la antigua contraseña."
                            mostrarSnack = true
                        }
                    } else {
                        mensajeSnack = "El código introducido no es Correcto."
                        mostrarSnack = true
                    }

                } catch (e: ApiServicesException) {
                    mensajeSnack = "No se ha podido cambiar la contraseña."
                    mostrarSnack = true
                }

                var navOptions = NavOptions.Builder().apply {
                    setPopUpTo(
                        HomeRoute, true, false
                    )

                }
                event.onNavigateToLogin?.invoke(
                    usuario.email,
                    navOptions.build()
                )
            }
        }
    }

}


