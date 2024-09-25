package com.pmdm.travelmate.data


import com.pmdm.travelmate.data.converters.toUsuario
import com.pmdm.travelmate.data.converters.toUsuarioApi
import com.pmdm.travelmate.data.services.usuario.UsuarioServiceImplementation
import com.pmdm.travelmate.models.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val proveedorUsuarios: UsuarioServiceImplementation
) {
    suspend fun getAll(): List<Usuario> =
        withContext(Dispatchers.IO) {
            proveedorUsuarios.get().toMutableList().map { it.toUsuario() }
        }

    suspend fun getByMail(login: String): Usuario? =
        withContext(Dispatchers.IO) { proveedorUsuarios.getByMail(login)?.toUsuario() }

    suspend fun getById(id: Int): Usuario? =
        withContext(Dispatchers.IO) { proveedorUsuarios.getById(id)?.toUsuario() }

    suspend fun insert(usuario: Usuario) =
        withContext(Dispatchers.IO) { proveedorUsuarios.insert(usuario.toUsuarioApi()) }

    suspend fun update(usuario: Usuario) =
        withContext(Dispatchers.IO) { proveedorUsuarios.update(usuario.toUsuarioApi()) }

    suspend fun delete(usuario: Usuario) =
        withContext(Dispatchers.IO) { proveedorUsuarios.delete(usuario.toUsuarioApi()) }
}