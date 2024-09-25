package com.pmdm.travelmate.data

import com.pmdm.travelmate.data.converters.toGasto
import com.pmdm.travelmate.data.converters.toGastoApi
import com.pmdm.travelmate.data.services.gasto.GastoServiceImplementation
import com.pmdm.travelmate.models.Gasto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GastoRepository @Inject constructor(
    private val proveedorGastos: GastoServiceImplementation
) {
    suspend fun getAll(): List<Gasto> =
        withContext(Dispatchers.IO) { proveedorGastos.get().toMutableList().map { it.toGasto() } }

    suspend fun getById(id: String): Gasto? =
        withContext(Dispatchers.IO) { proveedorGastos.getById(id).toGasto() }

    suspend fun insert(gasto: Gasto) =
        withContext(Dispatchers.IO) { proveedorGastos.insert(gasto.toGastoApi()) }

    suspend fun update(gasto: Gasto) =
        withContext(Dispatchers.IO) { proveedorGastos.update(gasto.toGastoApi()) }

    suspend fun delete(gasto: Gasto) =
        withContext(Dispatchers.IO) { proveedorGastos.delete(gasto.toGastoApi()) }
}
