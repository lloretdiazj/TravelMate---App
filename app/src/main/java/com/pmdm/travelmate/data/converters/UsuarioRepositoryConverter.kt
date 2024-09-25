package com.pmdm.travelmate.data.converters

import com.pmdm.travelmate.data.services.usuario.UsuarioApi

import com.pmdm.travelmate.models.Usuario
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.ui.features.newuser.newuserpassword.LoginPasswordUiState


//region usuarioEntity

fun UsuarioApi.toUsuario(): Usuario =
    Usuario(
        id = this.id,
        email = this.email,
        nombre = this.nombre,
        contrasena = this.contrasena,
        imagen = this.imagen
    )

fun Usuario.toUsuarioApi(): UsuarioApi =
    UsuarioApi(
        id = this.id,
        email = this.email,
        nombre = this.nombre,
        contrasena = this.contrasena,
        imagen = this.imagen
    )

fun Usuario.toUsuarioInicioUiState(): UsuarioInicioUiState =
    UsuarioInicioUiState(
        id = this.id,
        email = this.email,
        nombre = this.nombre,
        contrasena = this.contrasena,
        imagen = this.imagen
    )

fun UsuarioInicioUiState.toUsuario(): Usuario =
    Usuario(
        id = this.id,
        email = this.email,
        nombre = this.nombre,
        contrasena = this.contrasena,
        imagen = this.imagen
    )

fun LoginPasswordUiState.toUsuario(): Usuario =
    Usuario(
        id = 0,
        email = this.email,
        nombre = this.nombre,
        contrasena = this.contrasena,
        imagen = this.imagen
    )

//endregion


