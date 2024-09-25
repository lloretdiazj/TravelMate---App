package com.pmdm.travelmate.data.services.viaje

import com.pmdm.travelmate.data.services.usuario.UsuarioApi
import java.util.Date


data class ViajeApi(
    val id: Int,
    val fechaFin: String?,
    val fechaInicio: String?,
    val kilometrosRealizados: Int,
    val nombre: String,
    val usuarioId: UsuarioApi,
    val imagen: String? = null

)

