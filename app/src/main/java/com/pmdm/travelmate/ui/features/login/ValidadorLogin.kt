package com.pmdm.travelmate.ui.features.login

import com.pmdm.travelmate.utilities.validacion.Validador
import com.pmdm.travelmate.utilities.validacion.ValidadorCompuesto
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorCorreo
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorLogin @Inject constructor() : Validador<LoginUiState> {
    var validadorLogin =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El login no puede estar vacío"))
            .add(ValidadorCorreo("El correo no es válido"))

    var validadorPassword =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El password no puede estar vacío"))
            .add(ValidadorLongitudMinimaTexto(8, "El password debe tener como mínimo 8 carácteres"))

    override fun valida(datos: LoginUiState): ValidacionLoginUiState {
        val validacionLogin = validadorLogin.valida(datos.email)
        val validacionPassword = validadorPassword.valida(datos.contrasena)

        return ValidacionLoginUiState(
            validacionLogin = validacionLogin,
            validacionPassword = validacionPassword
        )
    }
}