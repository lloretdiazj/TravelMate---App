package com.pmdm.travelmate.di

import com.pmdm.travelmate.data.services.usuario.UsuarioService
import com.pmdm.travelmate.data.services.gasto.GastoService
import com.pmdm.travelmate.data.services.gasto.LugarService
import com.pmdm.travelmate.data.services.viaje.ViajeService


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val timeout = 10L
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }




    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://proyectodam.francecentral.cloudapp.azure.com:8080/travelm/datos/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //Azure: http://proyectodam.francecentral.cloudapp.azure.com:8080/travelm/datos/
    //http://{IP}:8080/travelm/datos/
    //Cable casa: http://192.168.0.127:8080/travelm/datos/
    //Wifi casa: http://192.168.0.169:8080/travelm/datos/

    @Provides
    @Singleton
    fun provideUsuariosService(
        retrofit: Retrofit
    ): UsuarioService = retrofit.create(UsuarioService::class.java)

    @Provides
    @Singleton
    fun provideLugaresService(
        retrofit: Retrofit
    ): LugarService = retrofit.create(LugarService::class.java)

    @Provides
    @Singleton
    fun provideViajesService(
        retrofit: Retrofit
    ): ViajeService = retrofit.create(ViajeService::class.java)

    @Provides
    @Singleton
    fun provideGastosService(
        retrofit: Retrofit
    ): GastoService = retrofit.create(GastoService::class.java)






}