package com.pmdm.travelmate.data.services.usuario

import android.util.Log
import com.pmdm.travelmate.data.services.ApiServicesException
import javax.inject.Inject

class UsuarioServiceImplementation @Inject constructor(
    private val usuarioService: UsuarioService
) {
    private val logTag: String = "OkHttp"
    suspend fun get(): List<UsuarioApi> {
        val mensajeError = "No se han podido obtener los usuarios"
        try {
            val response = usuarioService.get()
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()
                return dato ?: throw ApiServicesException("No hay datos")
            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

    suspend fun getById(id: Int): UsuarioApi {
        val mensajeError = "No se han podido obtener el usuario con id = $id"
        try {
            val response = usuarioService.get(id)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()

                return dato ?: throw ApiServicesException("No hay datos")

            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

    suspend fun getByMail(correo: String): UsuarioApi {
        val mensajeError = "No se han podido obtener el usuario con correo = $correo"
        try {
            val response = usuarioService.getEmail(correo)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()

                return dato ?: throw ApiServicesException("No hay datos")

            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }


    suspend fun insert(usuario: UsuarioApi) {
        val mensajeError = "No se ha podido añadir el usuario"
        try {
            val response = usuarioService.insert(usuario)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                // Aquí response.body() es un objeto de tipo RespuestaApi
                // que simplemente logeamos si no es null.
                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

    suspend fun update(usuario: UsuarioApi) {
        val mensajeError = "No se ha podido actualizar el usuario"
        try {
            val response = usuarioService.update(usuario)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                // Aquí response.body() es un objeto de tipo RespuestaApi
                // que simplemente logeamos si no es null.
                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }

    suspend fun delete(usuario: UsuarioApi) {
        val mensajeError = "No se ha podido eliminar el usuario"
        try {
            val response = usuarioService.delete(usuario.id!!)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                // Aquí response.body() es un objeto de tipo RespuestaApi
                // que simplemente logeamos si no es null.
                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            throw ApiServicesException(mensajeError)
        }
    }
}