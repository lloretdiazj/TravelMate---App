package com.pmdm.travelmate.data.services.gasto


import com.pmdm.travelmate.data.services.RespuestaApi
import com.pmdm.travelmate.data.services.lugar.LugarApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface LugarService {

    @POST("lugares")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun insert(@Body lugar: LugarApi): Response<RespuestaApi>

    @GET("lugares/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(@Path("id") id: String): Response<LugarApi>

    @GET("lugares")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun get(): Response<List<LugarApi>>

    @PUT("lugares/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun update(@Path("id") id: Int, @Body c: LugarApi): Response<RespuestaApi>

    @DELETE("lugares/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun delete(@Path("id") id: Int): Response<RespuestaApi>

}





