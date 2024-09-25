package com.pmdm.travelmate.ui.features.inicio.updateuser

import com.pmdm.travelmate.utilities.validacion.Validacion
import com.pmdm.travelmate.utilities.validacion.ValidacionCompuesta

data class ValidacionUpdateUsuario(
    val validacionLogin: Validacion = object : Validacion {},
    val validacionNombre: Validacion = object : Validacion {}
) : Validacion {
    private lateinit var validacionCompuesta: ValidacionCompuesta
    private fun componerValidacion(): ValidacionCompuesta {
        validacionCompuesta = ValidacionCompuesta()
            .add(validacionLogin)
            .add(validacionNombre)
        return validacionCompuesta
    }

    override val hayError: Boolean
        get() = componerValidacion().hayError
    override val mensajeError: String?
        get() = validacionCompuesta.mensajeError
}