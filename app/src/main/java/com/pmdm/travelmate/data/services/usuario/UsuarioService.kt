package com.pmdm.travelmate.data.services.usuario

import com.pmdm.travelmate.data.services.RespuestaApi
import com.pmdm.travelmate.data.services.usuario.UsuarioApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UsuarioService {

    @GET("usuarios")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<UsuarioApi>>

    @GET("usuarios/email/{login}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getEmail(@Path("login") login: String): Response<UsuarioApi>

    @GET("usuarios/id/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(@Path("id") id: Int): Response<UsuarioApi>

    @POST("usuarios")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insert(@Body usuario: UsuarioApi): Response<RespuestaApi>

    @PUT("usuarios")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun update(@Body c: UsuarioApi): Response<RespuestaApi>


    @DELETE("usuarios/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun delete(@Path("id") id: Int): Response<RespuestaApi>


}