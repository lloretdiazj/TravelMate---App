package com.pmdm.travelmate.utilities.validacion.validadores

import com.pmdm.travelmate.utilities.validacion.Validador
import com.pmdm.travelmate.utilities.validacion.Validacion


class ValidadorTextoNoVacio(
    val error: String = "El campo no puede estar vacío"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = texto.isEmpty()
            override val mensajeError: String
                get() = error
        }
    }
}
