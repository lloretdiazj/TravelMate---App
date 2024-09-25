package com.pmdm.travelmate.data.services

data class RespuestaApi(
    val respuesta: Int,
    val metodo: String? = null,
    val tabla: String? = null,
    val mensaje: String? = null,
    val sqlQuery: String? = null,
    val sqlError: String? = null
)