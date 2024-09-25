package com.pmdm.travelmate.data.services.gasto


import com.pmdm.travelmate.data.services.RespuestaApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface GastoService {

    @POST("gastos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insert(@Body gasto: GastoApi): Response<RespuestaApi>

    @GET("gastos/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(@Path("id") id: String): Response<GastoApi>

    @GET("gastos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<GastoApi>>

    @PUT("gastos/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun update(@Path("id") id: Int, @Body c: GastoApi): Response<RespuestaApi>

    @DELETE("gastos/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun delete(@Path("id") id: Int): Response<RespuestaApi>

}





