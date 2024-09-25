package com.pmdm.travelmate.data

import com.pmdm.travelmate.data.converters.toLugar
import com.pmdm.travelmate.data.converters.toLugarApi
import com.pmdm.travelmate.models.Lugar
import com.pmdm.travelmate.data.services.lugar.LugarServiceImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LugarRepository @Inject constructor(
    private val proveedorLugares: LugarServiceImplementation
) {
    suspend fun getAll(): List<Lugar> =
        withContext(Dispatchers.IO) { proveedorLugares.get().toMutableList().map { it.toLugar() } }

    suspend fun getById(id: String): Lugar? =
        withContext(Dispatchers.IO) { proveedorLugares.getById(id).toLugar() }

    suspend fun insert(lugar: Lugar) =
        withContext(Dispatchers.IO) { proveedorLugares.insert(lugar.toLugarApi()) }

    suspend fun update(lugar: Lugar) =
        withContext(Dispatchers.IO) { proveedorLugares.update(lugar.toLugarApi()) }

    suspend fun delete(lugar: Lugar) =
        withContext(Dispatchers.IO) { proveedorLugares.delete(lugar.toLugarApi()) }
}