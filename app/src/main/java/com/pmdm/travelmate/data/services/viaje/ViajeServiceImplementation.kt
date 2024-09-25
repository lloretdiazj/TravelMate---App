package com.pmdm.travelmate.data.services.viaje

import android.util.Log
import com.pmdm.travelmate.data.services.ApiServicesException
import javax.inject.Inject

class ViajeServiceImplementation @Inject constructor(
    private val viajesService: ViajeService
) {
    private val logTag: String = "OkHttp"
    suspend fun get(): List<ViajeApi> {
        val mensajeError = "No se han podido obtener los viajes"
        try {
            val response = viajesService.get()
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()
                return dato ?: throw ApiServicesException("No hay datos de viajes")
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

    suspend fun getById(id: Int): ViajeApi {
        val mensajeError = "No se han podido obtener el viaje con id = $id"
        try {
            val response = viajesService.getById(id)
            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()

                return dato ?: throw ApiServicesException("No hay datos de viajes con id = $id")

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

    suspend fun insert(viaje: ViajeApi) {
        val mensajeError = "No se ha podido añadir el viaje"
        try {
            val response = viajesService.insert(viaje)
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

    suspend fun update(viaje: ViajeApi) {
        val mensajeError = "No se ha podido actualizar el viaje"
        try {
            val response = viajesService.update(viaje)
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

    suspend fun delete(viaje: ViajeApi) {
        val mensajeError = "No se ha podido eliminar el viaje"
        try {
            val response = viajesService.delete(viaje.id)
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