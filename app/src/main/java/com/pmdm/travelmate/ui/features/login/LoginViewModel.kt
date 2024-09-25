package com.pmdm.travelmate.ui.features.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.services.ApiServicesException
import com.pmdm.travelmate.models.Usuario
import com.pmdm.travelmate.utilities.cifrado.toSHA256
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorLogin
) : ViewModel() {

    var usuarioUiState by mutableStateOf(LoginUiState())
        private set
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set
    var mostrarSnackBar by mutableStateOf(false)
    val onMostrarSnackBar: () -> Unit by mutableStateOf({
        mostrarSnackBar = !mostrarSnackBar
    })

    fun ocultaSnack() {
        mostrarSnackBar = false
    }

    var usuarioIdState by mutableStateOf("0")

    fun onLoginEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.LoginChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    email = loginEvent.login
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionLogin = validadorLogin.validadorLogin.valida(loginEvent.login)
                )
            }

            is LoginEvent.PasswordChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    contrasena = loginEvent.password
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionPassword = validadorLogin.validadorPassword.valida(loginEvent.password)
                )
            }

            is LoginEvent.OnClickNewUser -> {
                loginEvent.onNavigateToNewUser.invoke()
            }

            is LoginEvent.OnUpdatePassword -> {
                loginEvent.onNavigateToUpdatePassword.invoke()
            }

            is LoginEvent.OnClickLogearse -> {
                viewModelScope.launch {
                    validacionLoginUiState = validadorLogin.valida(usuarioUiState)
                    if (!validacionLoginUiState.hayError) {
                        usuarioUiState = usuarioUiState.copy(
                            estaLogeado = logearse()
                        )
                        if (usuarioUiState.estaLogeado) {
                            loginEvent.onNavigateInicio?.let { it(usuarioUiState.email) }
                        } else {
                            mostrarSnackBar = true
                        }

                    }


                }
            }
        }
    }

    suspend fun logearse(): Boolean {
        val usuario = usuarioUiState.toUsuario()
        try {
            usuarioRepository.getAll().forEach {
                if (usuario.email == it.email && usuario.contrasena.toSHA256() == it.contrasena) {

                    usuarioIdState = it.email
                    return true
                }
            }
        } catch (e: ApiServicesException) {
            Log.d("LoginViewModel", "Error al logearse: ${e.message}")
            return false
        }
        return false
    }

    fun iniciaUsuario(correo: String?) {
        if (correo != null) usuarioUiState = LoginUiState(0, correo, "", "", null, false)
        else usuarioUiState = LoginUiState()
        mostrarSnackBar = false
    }

    fun clearLogin() {
        usuarioUiState = LoginUiState()
    }

    fun LoginUiState.toUsuario() =
        Usuario(this.id, this.email, this.nombre, this.contrasena, this.imagen)
}