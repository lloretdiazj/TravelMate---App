package com.pmdm.travelmate.ui.features.inicio.updateuser

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.converters.toUsuario
import com.pmdm.travelmate.data.converters.toUsuarioInicioUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.utilities.cifrado.toSHA256
import com.pmdm.travelmate.utilities.images.Imagenes.Companion.androidBitmapToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorUpdateUsuario
) : ViewModel() {

    var usuarioUiState by mutableStateOf(UsuarioInicioUiState())
        private set
    var validacionUpdate by mutableStateOf(ValidacionUpdateUsuario())
    var imagen by mutableStateOf("")
    var nuevaContraseña by mutableStateOf("")
    var mostrarDialogoModificado by mutableStateOf(false)


    fun inicializaUsuario(idUsuario: Int) {
        Log.d("Usuario id", idUsuario.toString())
        viewModelScope.launch {
            usuarioUiState =
                usuarioRepository.getById(idUsuario)!!.toUsuarioInicioUiState()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun onFotoCambiada(image: ImageBitmap) {

        usuarioUiState = usuarioUiState.copy(
            imagen = androidBitmapToBase64(
                image.asAndroidBitmap()
            )
        )


    }

    fun onUpdateUserEvent(event: UpdateUserEvent) {
        when (event) {

            is UpdateUserEvent.OnEliminaImagen -> {
                usuarioUiState = usuarioUiState.copy(
                    imagen = ""
                )
            }

            is UpdateUserEvent.OnCambioNombre -> {

                usuarioUiState = usuarioUiState.copy(
                    nombre = event.nombre
                )

                validacionUpdate = validacionUpdate.copy(
                    validacionNombre = validadorLogin.validadorNombre.valida(event.nombre)
                )
            }

            is UpdateUserEvent.OnCambioEmail -> {
                usuarioUiState = usuarioUiState.copy(
                    email = event.email
                )

                validacionUpdate = validacionUpdate.copy(
                    validacionLogin = validadorLogin.validadorLogin.valida(event.email)
                )
            }

            is UpdateUserEvent.OnCambioPassword -> {
                nuevaContraseña = event.password
            }

            is UpdateUserEvent.OnDissmiss -> {
                mostrarDialogoModificado = false
            }

            is UpdateUserEvent.OnClickActualizarUsuario -> {

                if (nuevaContraseña != "") {
                    if (nuevaContraseña != usuarioUiState.contrasena) {
                        usuarioUiState = usuarioUiState.copy(
                            contrasena = nuevaContraseña.toSHA256()
                        )
                    }
                }

                viewModelScope.launch {
                    usuarioRepository.update(usuarioUiState.toUsuario())
                    nuevaContraseña = ""
                    usuarioUiState =
                        usuarioRepository.getById(usuarioUiState.id!!)!!.toUsuarioInicioUiState()
                    mostrarDialogoModificado = true
                }
            }
        }
    }
}

