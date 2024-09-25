package com.pmdm.travelmate.data


import com.pmdm.travelmate.data.converters.toViaje
import com.pmdm.travelmate.data.converters.toViajeApi
import com.pmdm.travelmate.data.services.viaje.ViajeServiceImplementation
import com.pmdm.travelmate.models.Viaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViajeRepository @Inject constructor(
    private val proveedorViajes: ViajeServiceImplementation
) {
    suspend fun getAll(): List<Viaje> =
        withContext(Dispatchers.IO) { proveedorViajes.get().toMutableList().map { it.toViaje() } }


    suspend fun getById(id: Int): Viaje? =
        withContext(Dispatchers.IO) { proveedorViajes.getById(id).toViaje() }

    suspend fun insert(viaje: Viaje) =
        withContext(Dispatchers.IO) { proveedorViajes.insert(viaje.toViajeApi()) }

    suspend fun update(viaje: Viaje) =
        withContext(Dispatchers.IO) { proveedorViajes.update(viaje.toViajeApi()) }

    suspend fun delete(viaje: Viaje) =
        withContext(Dispatchers.IO) { proveedorViajes.delete(viaje.toViajeApi()) }
}