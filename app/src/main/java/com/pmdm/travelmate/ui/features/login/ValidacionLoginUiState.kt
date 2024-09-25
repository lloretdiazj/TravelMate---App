package com.pmdm.travelmate.ui.features.login

import com.pmdm.travelmate.utilities.validacion.Validacion
import com.pmdm.travelmate.utilities.validacion.ValidacionCompuesta

data class ValidacionLoginUiState(
    val validacionLogin: Validacion = object : Validacion {},
    val validacionPassword: Validacion = object : Validacion {}
) : Validacion {
    private lateinit var validacionCompuesta: ValidacionCompuesta
    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionLogin)
            .add(validacionPassword)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}