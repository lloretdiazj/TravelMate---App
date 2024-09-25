package com.pmdm.travelmate.data.services.lugar

import android.util.Log
import com.pmdm.travelmate.data.services.ApiServicesException
import com.pmdm.travelmate.data.services.gasto.LugarService
import javax.inject.Inject

class LugarServiceImplementation @Inject constructor(
    private val lugaresService: LugarService
) {
    private val logTag: String = "OkHttp"
    suspend fun get(): List<LugarApi> {
        val mensajeError = "No se han podido obtener los lugares"
        try {
            val response = lugaresService.get()
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()
                return dato ?: throw ApiServicesException("No hay datos de lugares")
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

    suspend fun getById(id: String): LugarApi {
        val mensajeError = "No se han podido obtener el lugar con id = $id"
        try {
            val response = lugaresService.get(id)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()

                return dato ?: throw ApiServicesException("No hay datos de lugares con id = $id")

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

    suspend fun insert(lugar: LugarApi) {
        val mensajeError = "No se ha podido añadir el lugar"
        try {
            val response = lugaresService.insert(lugar)
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

    suspend fun update(lugar: LugarApi) {
        val mensajeError = "No se ha podido actualizar el lugar"
        try {
            val response = lugaresService.update(lugar.id, lugar)
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

    suspend fun delete(lugar: LugarApi) {
        val mensajeError = "No se ha podido eliminar el lugar"
        try {
            val response = lugaresService.delete(lugar.id)
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