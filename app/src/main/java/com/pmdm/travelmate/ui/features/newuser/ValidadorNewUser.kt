package com.pmdm.travelmate.ui.features.newuser

import com.pmdm.travelmate.ui.features.newuser.newuserpassword.ValidadorLoginPassword
import com.pmdm.travelmate.utilities.validacion.Validador
import javax.inject.Inject

class ValidadorNewUser @Inject constructor(
    val validadorLoginPassword: ValidadorLoginPassword
) : Validador<NewUserUiState> {
    override fun valida(datos: NewUserUiState): ValidacionNewUserUiState {
        val validacionLoginPassword = validadorLoginPassword.valida(datos.newUserPasswordUiState)

        return ValidacionNewUserUiState(
            validacionLoginPasswordUiState = validacionLoginPassword
        )
    }
}
