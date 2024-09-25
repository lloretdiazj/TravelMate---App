package com.pmdm.travelmate.utilities.validacion.validadores

import com.pmdm.travelmate.utilities.validacion.Validacion
import com.pmdm.travelmate.utilities.validacion.Validador

class ValidadorLongitudMinimaTexto(
    val tamañoMinimo: Int,
    val error: String = "El texto debe mayor o igual a ${tamañoMinimo}"
) : Validador<String> {
    override fun valida(texto: String): Validacion {
        return object : Validacion {
            override val hayError: Boolean
                get() = texto.length < tamañoMinimo
            override val mensajeError: String
                get() = error
        }
    }
}
