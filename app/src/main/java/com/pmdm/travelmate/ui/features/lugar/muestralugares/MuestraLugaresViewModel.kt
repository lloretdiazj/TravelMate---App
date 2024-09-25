package com.pmdm.travelmate.ui.features.lugar.muestralugares

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.pmdm.travelmate.data.GastoRepository
import com.pmdm.travelmate.data.LugarRepository
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.ViajeRepository
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class MuestraLugaresViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val gastoRepository: GastoRepository,
    private val viajeRepository: ViajeRepository,
    private val lugarRepository: LugarRepository,
) : ViewModel() {
    // contiene latitud y longitu de la ciudad
    // se inicializará más tarde
    lateinit var cityCoordinates: LatLng

    //Estado del viaje que contiene estos lugares
    var viajeState by mutableStateOf(ViajeUiState())

    // Inicializa la posicion de la camara a 0
    var cameraPositionState = CameraPositionState(
        CameraPosition(
            LatLng(0.toDouble(), 0.toDouble()), 12f, 10f, 10f
        )
    )

    // lista de SitioState
    var listaMarcadores by mutableStateOf(mutableListOf<SitioState>())

    //Función para obtener las coordenadas de una ciudad
    // Si no consigue encontrar el lugar en la API
    // Inicializa las coords a 0.0
    fun getCityCoordinates(context: Context, cityName: String) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocationName(cityName, 1)!!.toList()
        if (addresses.isNotEmpty()) {
            val address = addresses[0]
            cityCoordinates = LatLng(address.latitude, address.longitude)

        } else cityCoordinates = LatLng(0.toDouble(), 0.toDouble())
    }

    //Funcion llamada al navegar a la Screen
    //Le llega el contexto de la activity, un viaje y una lista de los lugares
    fun inicializaLugares(contexto: Context, viaje: ViajeUiState, lugares: List<LugarUiState>) {

        viewModelScope.launch {
            //Copiamos el viaje de este view model por el recibido
            viajeState = viaje
            //Obtiene coords con el nombre
            getCityCoordinates(contexto, viaje.nombre)
            //Actualiza la posición de la cámara a la ciudad del viaje
            cameraPositionState = CameraPositionState(
                CameraPosition(
                    LatLng(cityCoordinates.latitude, cityCoordinates.longitude), 10f, 10f, 10f
                )
            )
            // inicializamos la lista con los distintos marcadores
            listaMarcadores = lugares.map {
                SitioState(
                    it.nombre, it.latitud.toDouble(), it.longitud.toDouble()
                )
            }.toMutableList()
        }
    }
}