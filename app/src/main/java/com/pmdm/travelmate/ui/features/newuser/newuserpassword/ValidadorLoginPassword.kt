package com.pmdm.travelmate.ui.features.newuser.newuserpassword

import com.pmdm.travelmate.utilities.validacion.Validador
import com.pmdm.travelmate.utilities.validacion.ValidadorCompuesto
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorCorreo
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorTextoNoVacio

import javax.inject.Inject

class ValidadorLoginPassword @Inject constructor() : Validador<LoginPasswordUiState> {
    val validacionLogin = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El login no puede estar vacío"))
        .add(ValidadorCorreo("El login debe de ser un e-Mail"))
    val validacionNombre = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El nombre no puede estar vacío"))
        .add(ValidadorLongitudMinimaTexto(4, "El Nombre debe de tener al menos 4 carácteres"))

    val validacionPassword = ValidadorCompuesto<String>()
        .add(ValidadorTextoNoVacio("El password no puede estar vacío"))
        .add(ValidadorLongitudMinimaTexto(8, "El password debe de tener al menos 8 carácteres"))

    override fun valida(newUserPasswordUiState: LoginPasswordUiState): ValidacionLoginPasswordUiState {
        val validacionLogin = validacionLogin.valida(newUserPasswordUiState.email)
        val validacionNombre = validacionNombre.valida(newUserPasswordUiState.nombre)
        val validacionPassword = validacionPassword.valida(newUserPasswordUiState.contrasena)

        return ValidacionLoginPasswordUiState(
            validacionLogin = validacionLogin,
            validacionNombre = validacionNombre,
            validacionPassword = validacionPassword
        )
    }
}