package com.pmdm.travelmate.ui

import com.pmdm.travelmate.models.Usuario
import com.pmdm.travelmate.ui.features.login.LoginUiState

fun Usuario.toUsuarioUiState(logeado: Boolean) =
    LoginUiState(this.id, this.email, this.nombre, this.contrasena, this.imagen, logeado)
//endregion

//region UsuarioUiState
fun LoginUiState.toUsuario() =
    Usuario(this.id, this.email, this.nombre, this.contrasena, this.imagen)
//endregion