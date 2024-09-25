package com.pmdm.travelmate.ui.features.updateviaje

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.travelmate.data.GastoRepository
import com.pmdm.travelmate.data.LugarRepository
import com.pmdm.travelmate.data.ViajeRepository
import com.pmdm.travelmate.data.converters.toViaje
import com.pmdm.travelmate.data.converters.toViajeUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import com.pmdm.travelmate.utilities.images.Imagenes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViajeViewModel @Inject constructor(
    private val viajeRepository: ViajeRepository,
    private val gastoRepository: GastoRepository,
    private val lugarRepository: LugarRepository,
) : ViewModel() {

    var viajeState by mutableStateOf(ViajeUiState())
    var fechaInicioState by mutableStateOf("")
    var fechaFinState by mutableStateOf("")
    var verDialogoFechaInicio by mutableStateOf(false)
    var verDialogoFechaFin by mutableStateOf(false)
    var verDialogoCambioRealizado by mutableStateOf(false)
    var cantidadGastos by mutableStateOf(0)
    var cantidadLugares by mutableStateOf(0)
    var gastoUiState by mutableStateOf(GastoUiState(0, "", 0f, ViajeUiState()))


    fun inicializaViaje(idViaje: Int) {

        viewModelScope.launch {
            viajeState = viajeRepository.getById(idViaje)!!.toViajeUiState()
            fechaInicioState = viajeState.fechaInicio!!
            fechaFinState = viajeState.fechaFin!!
            numeroGastosLugaresPorViaje(viajeState.id)
        }
    }

    fun numeroGastosLugaresPorViaje(idViaje: Int) {
        viewModelScope.launch {
            cantidadGastos = gastoRepository
                .getAll()
                .filter { it.viajeId.id == idViaje }
                .size

            cantidadLugares = lugarRepository
                .getAll()
                .filter { it.viajeId.id == idViaje }
                .size
        }
        Log.d("gastos vm", "Gastos por viaje: ${gastoUiState.gasto}")
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun onFotoCambiada(image: ImageBitmap) {

        viajeState = viajeState.copy(
            imagen = Imagenes.androidBitmapToBase64(
                image.asAndroidBitmap()
            )
        )
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

    @OptIn(ExperimentalMaterial3Api::class)
    fun onViajeUpdateEvent(event: UpdateViajeEvent) {
        when (event) {
            is UpdateViajeEvent.OnNombreViajeChange -> {
                viajeState = viajeState.copy(nombre = event.nombre)
            }

            is UpdateViajeEvent.OnFechaInicioChange -> {
                if (event.fechaInicio?.selectedDateMillis != null)
                    fechaInicioState = datePickerToFecha(event.fechaInicio)
                verDialogoFechaInicio = false
            }

            is UpdateViajeEvent.OnFechaFinChange -> {
                if (event.fechaFin?.selectedDateMillis != null)
                    fechaFinState = datePickerToFecha(event.fechaFin)
                verDialogoFechaFin = false
            }

            is UpdateViajeEvent.OnDissmisFecha -> {
                verDialogoFechaInicio = false
                verDialogoFechaFin = false
            }

            is UpdateViajeEvent.OnKilometrosRealizadosEvent -> {
                viajeState =
                    viajeState.copy(
                        kilometrosRealizados =
                        if (event.kilometros != "")
                            event.kilometros.toInt()
                        else 0
                    )
            }

            is UpdateViajeEvent.OnEliminaImagen -> {
                viajeState = viajeState.copy(imagen = "")
            }

            is UpdateViajeEvent.OnDismiss -> {
                verDialogoCambioRealizado = false
            }

            is UpdateViajeEvent.OnClickFechaInicio -> {
                verDialogoFechaInicio = true
            }

            is UpdateViajeEvent.OnClickFechaFin -> {
                verDialogoFechaFin = true
            }

            is UpdateViajeEvent.OnClickUpdateViaje -> {
                viewModelScope.launch {
                    viajeState = viajeState.copy(
                        fechaInicio = fechaInicioState,
                        fechaFin = fechaFinState
                    )

                    viajeRepository.update(viajeState.toViaje())
                }
                verDialogoCambioRealizado = true
            }
        }
    }
}