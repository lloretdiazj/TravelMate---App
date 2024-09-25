package com.pmdm.travelmate.ui.features.inicio.updateuser

import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.utilities.validacion.Validador
import com.pmdm.travelmate.utilities.validacion.ValidadorCompuesto
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorCorreo
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorLongitudMinimaTexto
import com.pmdm.travelmate.utilities.validacion.validadores.ValidadorTextoNoVacio
import javax.inject.Inject

class ValidadorUpdateUsuario @Inject constructor() : Validador<UsuarioInicioUiState> {
    var validadorLogin =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El login no puede estar vacío"))
            .add(ValidadorCorreo("El correo no es válido"))

    var validadorNombre =
        ValidadorCompuesto<String>()
            .add(ValidadorTextoNoVacio("El nombre no puede estar vacío"))
            .add(ValidadorLongitudMinimaTexto(4, "El password debe tener como mínimo 4 carácteres"))

    override fun valida(datos: UsuarioInicioUiState): ValidacionUpdateUsuario {
        val validacionLogin = validadorLogin.valida(datos.email)
        val validacionNombre = validadorNombre.valida(datos.nombre)

        return ValidacionUpdateUsuario(
            validacionLogin = validacionLogin,
            validacionNombre = validacionNombre
        )
    }
}