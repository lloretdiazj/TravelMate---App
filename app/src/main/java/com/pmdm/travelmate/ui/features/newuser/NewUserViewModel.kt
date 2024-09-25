package com.pmdm.travelmate.ui.features.newuser

//import com.pmdm.tienda.data.ClienteRepository
//import com.pmdm.tienda.data.UsuarioRepository
//import com.pmdm.travelmate.data.services.ApiServicesException
//import com.pmdm.tienda.models.Cliente
//import com.pmdm.tienda.models.Direccion
//import com.pmdm.recetas.ui.features.newuser.datospersonales.DatosPersonalesEvent

//import com.pmdm.recetas.ui.navigation.HomeRoute
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.converters.toUsuario
import com.pmdm.travelmate.data.services.ApiServicesException
import com.pmdm.travelmate.models.Usuario
import com.pmdm.travelmate.ui.features.newuser.newuserpassword.LoginPasswordUiState
import com.pmdm.travelmate.ui.features.newuser.newuserpassword.NewUserPasswordEvent
import com.pmdm.travelmate.ui.features.newuser.newuserpassword.ValidadorLoginPassword
import com.pmdm.travelmate.ui.navigation.HomeRoute
import com.pmdm.travelmate.utilities.cifrado.toSHA256
import com.pmdm.travelmate.utilities.email.Email
import com.pmdm.travelmate.utilities.images.Imagenes.Companion.androidBitmapToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorNewUserPassword: ValidadorLoginPassword,
    private val validadorNewUser: ValidadorNewUser
) : ViewModel() {
    var esNuevoUsuario by mutableStateOf(true)
    var mostrarSnackState by mutableStateOf(false)
    var mensajeSnackBarState by mutableStateOf("")
    var estaCreadaCuenta: Boolean = false
    var newUserUiState by mutableStateOf(NewUserUiState())
    var validacionNewUserUiState by mutableStateOf(ValidacionNewUserUiState())
    var email by mutableStateOf(Email)

    @RequiresApi(Build.VERSION_CODES.R)
    fun onFotoCambiada(image: ImageBitmap) {

        newUserUiState = newUserUiState.copy(
            newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                imagen = androidBitmapToBase64(
                    image.asAndroidBitmap()
                )
            )
        )

    }

    fun onNewUserPasswordEvent(event: NewUserPasswordEvent) {
        when (event) {
            is NewUserPasswordEvent.LoginChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                        email = event.login
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionLoginPasswordUiState = validacionNewUserUiState.validacionLoginPasswordUiState.copy(
                        validacionLogin = validadorNewUserPassword.validacionLogin.valida(event.login)
                    )
                )
            }

            is NewUserPasswordEvent.PasswordChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                        contrasena = event.password,
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionLoginPasswordUiState = validacionNewUserUiState.validacionLoginPasswordUiState.copy(
                        validacionPassword = validadorNewUserPassword.validacionPassword.valida(
                            event.password
                        )
                    )
                )
            }

            is NewUserPasswordEvent.NombreChanged -> {
                mostrarSnackState = false
                mensajeSnackBarState = ""
                newUserUiState = newUserUiState.copy(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                        nombre = event.nombre,
                    )
                )
                validacionNewUserUiState = validacionNewUserUiState.copy(
                    validacionLoginPasswordUiState = validacionNewUserUiState.validacionLoginPasswordUiState.copy(
                        validacionNombre = validadorNewUserPassword.validacionNombre.valida(
                            event.nombre
                        )
                    )
                )
            }

            is NewUserPasswordEvent.OnEliminaImagen -> {
                newUserUiState = newUserUiState.copy(
                    newUserPasswordUiState = newUserUiState.newUserPasswordUiState.copy(
                        imagen = ""
                    )
                )
            }
            //Acción realizada al pulsar en el botón de crear usuario.
            is NewUserPasswordEvent.onClickCrearUsuario -> {
                viewModelScope.launch {// Creamos una corutina viewModelScope
                    // Indicamos que la Snack no se debe mostrar
                    mostrarSnackState = false
                    // Verifica que están los datos con el formato correcto
                    validacionNewUserUiState = validadorNewUser.valida(newUserUiState)
                    if (validacionNewUserUiState.hayError) {
                        //Si hay error se muestra la Snack con el mensaje del error del formato
                        mensajeSnackBarState = validacionNewUserUiState.mensajeError!!
                        mostrarSnackState = true
                    } else {
                        //Llama a la función creaCuenta(), con los datos introducidos por el usuario
                        // intenta crear un nuevo usuario
                        creaCuenta()
                        //Si la funcion anterior anterior consigue
                        // crear el usuario, estaCreadaCuenta = true
                        if (estaCreadaCuenta) {
                            // Indicamos al usuario que se ha creado la cuenta correctamente
                            mensajeSnackBarState = "Cuenta creada correctamente"
                            mostrarSnackState = true

                            viewModelScope.launch {
                                delay(4000)
                                //Dejamos de mostrar el Snack
                                mostrarSnackState = false
                                // Envía correo bienvenida
                                email.sendEmailWelcome(newUserUiState.newUserPasswordUiState.toUsuario())
                                //Configura la opciones para la navegación
                                var navOptions = NavOptions.Builder().apply {
                                    setPopUpTo(
                                        HomeRoute, true, false
                                    )
                                }
                                // Invoca el screen enviando el mail, para luego tenerlo al volver al Login
                                event.onNavigateToLogin?.invoke(
                                    newUserUiState.newUserPasswordUiState.email,
                                    navOptions.build()
                                )
                            }
                        } else {

                            // Si el mail ya existe se informa al usuario de que debe escribir otro
                            mensajeSnackBarState = "Ese usuario con ese mail ya existe"
                            mostrarSnackState = true
                        }
                    }
                }
            }
        }
    }

    suspend private fun creaCuenta() {

        estaCreadaCuenta = false
        var usuarios = mutableListOf<Usuario>()
        try {
            usuarios = usuarioRepository.getAll().toMutableList()
        } catch (e: ApiServicesException) {
            mensajeSnackBarState = "No se ha podido obtener los usuarios"
            mostrarSnackState = true
        }

        val loginAnterior =
            usuarios.find { it.email == newUserUiState.newUserPasswordUiState.email }

        if (loginAnterior == null) {
            val usuario = creaUsuario()
            usuarioRepository.insert(usuario.toUsuario())
            estaCreadaCuenta = true
        }
    }

    private fun creaUsuario(): LoginPasswordUiState =
        LoginPasswordUiState(
            newUserUiState.newUserPasswordUiState.email,
            newUserUiState.newUserPasswordUiState.nombre,
            newUserUiState.newUserPasswordUiState.contrasena.toSHA256(),
            newUserUiState.newUserPasswordUiState.imagen
        )


    fun clearUsuario() {
        newUserUiState = NewUserUiState()
        estaCreadaCuenta = false
        esNuevoUsuario = true
    }
}