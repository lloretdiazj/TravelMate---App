package com.pmdm.travelmate.ui.features.newuser


import com.pmdm.travelmate.ui.features.newuser.newuserpassword.ValidacionLoginPasswordUiState
import com.pmdm.travelmate.utilities.validacion.Validacion
import com.pmdm.travelmate.utilities.validacion.ValidacionCompuesta

data class ValidacionNewUserUiState(
    val validacionLoginPasswordUiState: ValidacionLoginPasswordUiState = ValidacionLoginPasswordUiState()
) : Validacion {
    private lateinit var validacionCompuesta: ValidacionCompuesta

    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionLoginPasswordUiState)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}
