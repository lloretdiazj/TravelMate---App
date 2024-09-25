package com.pmdm.travelmate.utilities.validacion.validadores

import com.pmdm.travelmate.utilities.validacion.Validacion
import com.pmdm.travelmate.utilities.validacion.Validador

class ValidadorCorreo(
    val error: String = "Correo no válido"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = !Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})$").matches(texto)
            override val mensajeError: String
                get() = error
        }
    }
}