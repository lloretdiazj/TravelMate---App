package com.pmdm.travelmate.ui.features.lugar

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.pmdm.travelmate.data.LugarRepository
import com.pmdm.travelmate.data.ViajeRepository
import com.pmdm.travelmate.data.converters.toLugar
import com.pmdm.travelmate.data.converters.toLugarUiState
import com.pmdm.travelmate.data.converters.toViajeUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class LugarViewModel @Inject constructor(
    private val viajeRepository: ViajeRepository,
    private val lugarRepository: LugarRepository
) : ViewModel() {

    var listaLugares by mutableStateOf(mutableListOf<LugarUiState>().toMutableStateList())
        private set
    var viajeUistate by mutableStateOf(ViajeUiState())
    var lugarViewModel by mutableStateOf(LugarUiState())
    var placesClient: PlacesClient? = null
    var verDialogoEliminacion by mutableStateOf(false)
    var verDialogoCreacion by mutableStateOf(false)

    fun iniciaLugares(viajeId: Int, context: Context) {
        viewModelScope.launch {
            listaLugares = lugarRepository.getAll().filter { it.viajeId.id == viajeId }
                .toMutableList().map { it.toLugarUiState() }.toMutableStateList()

            viajeUistate = viajeRepository.getById(viajeId)!!.toViajeUiState()

            placesClient = Places.createClient(context)
        }
    }


    fun searchPlaceByName(placeName: String) = viewModelScope.async {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(placeName + " " + viajeUistate.nombre)

            .build()
        var placeId = ""

        val predictionsResponse = withContext(Dispatchers.IO) {
            suspendCoroutine<FindAutocompletePredictionsResponse> { cont ->
                placesClient?.findAutocompletePredictions(request)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result!!)
                    } else {
                        cont.resumeWithException(task.exception!!)
                    }
                }
            }
        }
        if (predictionsResponse.autocompletePredictions.isNotEmpty()) {
            val prediction = predictionsResponse.autocompletePredictions[0]
            placeId = prediction.placeId
        }

        val placeFields = listOf(Place.Field.LAT_LNG)
        val fetchPlaceRequest = FetchPlaceRequest.newInstance(placeId, placeFields)
        var latLng = LatLng(0.toDouble(), 0.toDouble())

        if (placeId != "") {
            val fetchPlaceResponse = withContext(Dispatchers.IO) {
                suspendCoroutine<FetchPlaceResponse> { cont ->
                    placesClient?.fetchPlace(fetchPlaceRequest)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            cont.resume(task.result!!)
                        } else {
                            cont.resumeWithException(task.exception!!)
                        }
                    }
                }
            }
            if (fetchPlaceResponse.place != null) {
                latLng = fetchPlaceResponse.place.latLng!!
            }
        }
        latLng
    }


    fun actualizaLugares() {
        viewModelScope.launch {
            listaLugares = lugarRepository.getAll().filter { it.viajeId.id == viajeUistate.id }
                .toMutableList().map { it.toLugarUiState() }.toMutableStateList()
        }
    }

    fun onLugaresEvent(event: LugaresEvent) {
        when (event) {
            is LugaresEvent.EliminaLugar -> {
                viewModelScope.launch {
                    var lugarSeleccionado = lugarRepository.getById(event.id.toString())
                    lugarRepository.delete(lugarSeleccionado!!)
                    listaLugares =
                        lugarRepository.getAll().filter { it.viajeId.id == viajeUistate.id }
                            .toMutableList().map { it.toLugarUiState() }.toMutableStateList()
                    verDialogoEliminacion = true
                }
            }

            is LugaresEvent.OnCambioNombre -> {
                lugarViewModel = lugarViewModel.copy(nombre = event.nombre)
            }

            is LugaresEvent.OnDissmiss -> {
                verDialogoEliminacion = false
                verDialogoCreacion = false
            }

            is LugaresEvent.CreaLugar -> {
                viewModelScope.launch {
                    var latLng = searchPlaceByName(lugarViewModel.nombre).await()
                    if (lugarViewModel.nombre.isEmpty()) {
                        lugarViewModel =
                            lugarViewModel.copy(nombre = "Lugar ${listaLugares.size + 1}")
                    }
                    lugarViewModel = lugarViewModel.copy(
                        latitud = latLng.latitude.toFloat(),
                        longitud = latLng.longitude.toFloat(),
                        viajeId = viajeUistate
                    )
                    lugarRepository.insert(lugarViewModel.toLugar())
                    verDialogoCreacion = true
                    listaLugares =
                        lugarRepository.getAll().filter { it.viajeId.id == viajeUistate.id }
                            .toMutableList().map { it.toLugarUiState() }.toMutableStateList()
                    lugarViewModel = lugarViewModel.copy(
                        nombre = "",
                        latitud = 0f,
                        longitud = 0f,
                        viajeId = viajeUistate
                    )
                }
            }
        }
    }
}


