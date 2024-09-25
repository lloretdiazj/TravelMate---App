package com.pmdm.travelmate.ui.features.inicio

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.travelmate.data.GastoRepository
import com.pmdm.travelmate.data.LugarRepository
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.ViajeRepository
import com.pmdm.travelmate.data.converters.toGastos
import com.pmdm.travelmate.data.converters.toGastosUiState
import com.pmdm.travelmate.data.converters.toLugar
import com.pmdm.travelmate.data.converters.toLugarUiState
import com.pmdm.travelmate.data.converters.toUsuario
import com.pmdm.travelmate.data.converters.toUsuarioInicioUiState
import com.pmdm.travelmate.data.converters.toViaje
import com.pmdm.travelmate.data.converters.toViajeUiState
import com.pmdm.travelmate.data.services.ApiServicesException
import com.pmdm.travelmate.ui.features.inicio.configuracion.ConfiguracionEvent
import com.pmdm.travelmate.ui.features.inicio.creaviaje.CreaViajeEvent
import com.pmdm.travelmate.ui.features.inicio.listaviaje.ListaViajesEvent
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.LugarUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.UsuarioInicioUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import com.pmdm.travelmate.utilities.images.Imagenes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class InicioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val gastoRepository: GastoRepository,
    private val viajeRepository: ViajeRepository,
    private val lugarRepository: LugarRepository
) : ViewModel() {

    var usuarioState by mutableStateOf(UsuarioInicioUiState())
    var indiceState by mutableStateOf(0)
    var viajeState by mutableStateOf(ViajeUiState())
    var viajeStateMaps by mutableStateOf(ViajeUiState())
    var listaViajesState by mutableStateOf(mutableListOf<ViajeUiState>().toMutableStateList())
    var listaGastosState by mutableStateOf(mutableListOf<GastoUiState>().toMutableStateList())
    var listaLugaresState by mutableStateOf(mutableListOf<LugarUiState>().toMutableStateList())
    var fechaInicioState by mutableStateOf("")
    var fechaFinState by mutableStateOf("")
    var verDialogoFechaInicio by mutableStateOf(false)
    var verDialogoFechaFin by mutableStateOf(false)
    var idSeleccionado by mutableStateOf(0)
    var esItemSeleccionado by mutableStateOf(false)
    var verDialogoUsuario by mutableStateOf(false)
    var verDialogoEliminacionUsuario by mutableStateOf(false)
    var verDialogoSalirApp by mutableStateOf(false)
    var correoUsuario by mutableStateOf("")
    var verDialogoViajeCreado by mutableStateOf(false)
    var cantidadGastos by mutableStateOf(0)
    var cantidadLugares by mutableStateOf(0)
    var verDialogoViajeEliminado by mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.R)
    fun onFotoCambiada(image: ImageBitmap) {

        viajeState = viajeState.copy(
            imagen = Imagenes.androidBitmapToBase64(
                image.asAndroidBitmap()
            )
        )


    }

    fun numeroGastosLugaresPorViaje(idViaje: Int) {
        viewModelScope.launch {
            cantidadGastos = gastoRepository.getAll().filter { it.viajeId.id == idViaje }.size

            cantidadLugares = lugarRepository.getAll().filter { it.viajeId.id == idViaje }.size
        }
    }


    fun onInicioEvent(event: InicioEvent) {
        when (event) {
            is InicioEvent.OnNavigationScreen -> {
                indiceState = event.index
            }

            is InicioEvent.OnUpdateUser -> {

                event.onNavigateToUpdateUser.invoke(usuarioState.id!!)
            }
        }
    }

    suspend fun listaViajes(usuarioId: Int) =
        viajeRepository.getAll().filter { it.usuarioId.id == usuarioId }.toMutableList()
            .map { it.toViajeUiState() }.toMutableStateList()

    suspend fun listaGastos(usuarioId: Int) =
        gastoRepository.getAll().filter { it.viajeId.usuarioId.id == usuarioId }.toMutableList()
            .map { it.toGastosUiState() }.toMutableStateList()

    suspend fun listaLugares(usuarioId: Int) =
        lugarRepository.getAll().filter { it.viajeId.usuarioId.id == usuarioId }.toMutableList()
            .map { it.toLugarUiState() }.toMutableStateList()

    fun actualizaUsuario() {
        viewModelScope.launch {
            usuarioState = usuarioRepository.getByMail(correoUsuario)?.toUsuarioInicioUiState()!!
        }
    }

    fun actualizaGastos() {
        viewModelScope.launch {
            listaGastosState = listaGastos(usuarioState.id!!)
        }
    }

    fun actualizaLugares() {
        viewModelScope.launch {
            listaLugaresState = listaLugares(usuarioState.id!!)
        }
    }

    fun actualizaViajes() {
        viewModelScope.launch {
            listaViajesState = listaViajes(usuarioState.id!!)
        }
    }

    fun iniciaEstadoNavBar() {
        indiceState = 0
    }

    fun restableceEstadoNavBar() {
        esItemSeleccionado = false
    }

    fun iniciaUsuario(correo: String) {
        correoUsuario = correo
        viewModelScope.launch {

            usuarioState = usuarioRepository.getByMail(correo)?.toUsuarioInicioUiState()!!
            Log.d("InicioViewModel", "iniciaUsuario: ${usuarioState}")

            listaViajesState = listaViajes(usuarioState.id!!)
            listaGastosState = listaGastos(usuarioState.id!!)
            listaLugaresState = listaLugares(usuarioState.id!!)


            Log.d("InicioViewModel", "iniciaViajes: $listaViajesState")
            Log.d("InicioViewModel", "iniciaGastos: ${listaGastosState.forEach { it.toString() }}")

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun datePickerToFecha(fechaEstado: DatePickerState?): String {
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val calendar = Calendar.getInstance()
        if (fechaEstado?.selectedDateMillis != null) {
            calendar.timeInMillis = fechaEstado.selectedDateMillis!!

        }
        return formatter.format(calendar.time)
    }

    fun onConfiguracionEvent(event: ConfiguracionEvent) {
        when (event) {
            is ConfiguracionEvent.OnEliminaUsuarioEvent -> {
                viewModelScope.launch {
                    Log.d("usuario", usuarioState.id.toString())
                    if (usuarioState.id != null) {
                        try {
                            listaViajesState.forEach { viajeUiState ->
                                listaGastosState.forEach {
                                    if (viajeUiState.id == it.viajeId.id) gastoRepository.delete(it.toGastos())
                                }
                                listaLugaresState.forEach {
                                    if (viajeUiState.id == it.viajeId.id) lugarRepository.delete(it.toLugar())
                                }
                                viajeRepository.delete(viajeUiState.toViaje())
                            }
                            usuarioRepository.delete(usuarioState.toUsuario())
                        } catch (e: ApiServicesException) {

                        }

//                        }
                    }
                }
            }

            is ConfiguracionEvent.OnClickUsuario -> {
                verDialogoUsuario = !verDialogoUsuario
            }

            is ConfiguracionEvent.OnClickEliminaUsuario -> {
                verDialogoEliminacionUsuario = !verDialogoEliminacionUsuario
            }

            is ConfiguracionEvent.OnVerDialogoSalir -> {
                verDialogoSalirApp = !verDialogoSalirApp
            }
        }
    }

    fun onListaViajes(event: ListaViajesEvent) {
        when (event) {
            is ListaViajesEvent.onEliminaViaje -> {
                viewModelScope.launch {
                    Log.d("id", event.id.toString())
                    var viaje = viajeRepository.getById(event.id)
                    if (viaje != null) {
                        try {
                            listaGastosState.forEach {
                                if (viaje.id == it.viajeId.id) gastoRepository.delete(it.toGastos())
                            }
                            listaLugaresState.forEach {
                                if (viaje.id == it.viajeId.id) lugarRepository.delete(it.toLugar())
                            }
                            viajeRepository.delete(viaje)
                            verDialogoViajeEliminado = true
                        } catch (e: ApiServicesException) {

                        }

                    }
                    try {
                        listaGastosState = listaGastos(usuarioState.id!!)
                        listaLugaresState = listaLugares(usuarioState.id!!)
                        listaViajesState = listaViajes(usuarioState.id!!)
                    } catch (e: ApiServicesException) {

                    }

                }

                esItemSeleccionado = false
            }

            is ListaViajesEvent.OnDissmiss -> {
                verDialogoViajeEliminado = false
            }

            is ListaViajesEvent.onItemSeleccionado -> {

                viewModelScope.launch {
                    idSeleccionado = event.index
                    try {
                        viajeStateMaps = viajeRepository.getById(event.index)!!.toViajeUiState()
                    } catch (e: ApiServicesException) {

                    }

                    esItemSeleccionado = !esItemSeleccionado
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onCreaViajeEvent(event: CreaViajeEvent) {
        when (event) {
            is CreaViajeEvent.OnEliminaImagen -> {
                viajeState = viajeState.copy(
                    imagen = ""
                )
            }

            is CreaViajeEvent.OnDissmis -> {
                verDialogoViajeCreado = false
            }

            is CreaViajeEvent.OnNombreViajeChange -> {
                viajeState = viajeState.copy(nombre = event.nombre)
            }

            is CreaViajeEvent.OnFechaInicioChange -> {
                if (event.fechaInicio?.selectedDateMillis != null) fechaInicioState =
                    datePickerToFecha(event.fechaInicio)
                verDialogoFechaInicio = false
            }

            is CreaViajeEvent.OnFechaFinChange -> {
                if (event.fechaFin?.selectedDateMillis != null) fechaFinState =
                    datePickerToFecha(event.fechaFin)
                verDialogoFechaFin = false
            }

            is CreaViajeEvent.OnDissmisFecha -> {
                verDialogoFechaInicio = false
                verDialogoFechaFin = false
            }

            is CreaViajeEvent.OnKilometrosRealizadosEvent -> {
                viajeState = viajeState.copy(
                    kilometrosRealizados = if (event.kilometros != "") event.kilometros.toInt()
                    else 0
                )
            }

            is CreaViajeEvent.OnClickFechaInicio -> {
                verDialogoFechaInicio = true
            }

            is CreaViajeEvent.OnClickFechaFin -> {
                verDialogoFechaFin = true
            }

            is CreaViajeEvent.OnClickCrearViaje -> {

                if (viajeState.nombre.isEmpty()) {
                    viajeState = viajeState.copy(nombre = "Viaje nº${listaViajesState.size + 1}")
                }
                viewModelScope.launch {
                    viajeState = viajeState.copy(
                        usuarioId = usuarioState,
                        fechaInicio = fechaInicioState,
                        fechaFin = fechaFinState
                    )
                    try {
                        viajeRepository.insert(viajeState.toViaje())
                        listaViajesState = listaViajes(usuarioState.id!!)
                        listaGastosState = listaGastos(usuarioState.id!!)
                    } catch (e: ApiServicesException) {

                    }


                }
                verDialogoViajeCreado = true
                viajeState = viajeState.copy(
                    id = 0,
                    fechaFin = "",
                    fechaInicio = "",
                    kilometrosRealizados = 0,
                    nombre = "",
                    imagen = ""
                )
                fechaFinState = ""
                fechaInicioState = ""

            }
        }
    }
}