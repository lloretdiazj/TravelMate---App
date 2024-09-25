package com.pmdm.travelmate.utilities.validacion.validadores

import com.pmdm.travelmate.utilities.validacion.Validacion
import com.pmdm.travelmate.utilities.validacion.Validador

class ValidadorTelefono(
    val error: String = "Teléfono no válido"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = !Regex("^[0-9 ]{9,18}$").matches(texto)
            override val mensajeError: String
                get() = error
        }
    }
}