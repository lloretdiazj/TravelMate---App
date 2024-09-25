package com.pmdm.travelmate.data.services.viaje


import com.pmdm.travelmate.data.services.RespuestaApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ViajeService {

    @POST("viajes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insert(@Body viaje: ViajeApi): Response<RespuestaApi>

    @GET("viajes/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getById(@Path("id") id: Int): Response<ViajeApi>

    @GET("viajes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<ViajeApi>>

    @PUT("viajes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun update(@Body c: ViajeApi): Response<RespuestaApi>

    @DELETE("viajes/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun delete(@Path("id") id: Int): Response<RespuestaApi>

}





