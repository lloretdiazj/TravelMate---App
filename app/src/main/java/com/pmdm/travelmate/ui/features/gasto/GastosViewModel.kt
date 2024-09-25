package com.pmdm.travelmate.ui.features.gasto

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.travelmate.data.GastoRepository
import com.pmdm.travelmate.data.UsuarioRepository
import com.pmdm.travelmate.data.ViajeRepository
import com.pmdm.travelmate.data.converters.toGastos
import com.pmdm.travelmate.data.converters.toGastosUiState
import com.pmdm.travelmate.data.converters.toViajeUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.GastoUiState
import com.pmdm.travelmate.ui.features.inicio.uistates.ViajeUiState
import com.pmdm.travelmate.ui.features.login.ValidadorLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GastosViewModel @Inject constructor(
    private val gastoRepository: GastoRepository,
    private val viajeRepository: ViajeRepository
) : ViewModel() {

    var listaGasto by mutableStateOf(mutableListOf<GastoUiState>().toMutableStateList())
        private set
    var gastoState by mutableStateOf(GastoUiState())
        private set
    var viajeState by mutableStateOf(ViajeUiState())
        private set
    var muestraDialogoEliminacion by mutableStateOf(false)
    var muestraDialogoCreacion by mutableStateOf(false)

    suspend fun listaGastos(viajeId: Int) =
        gastoRepository.getAll().filter { it.viajeId.id == viajeId }
            .toMutableList().map { it.toGastosUiState() }.toMutableStateList()

    fun inicializaGastos(viajeId: Int) {
        viewModelScope.launch {
            viajeState = viajeRepository.getById(viajeId)!!.toViajeUiState()
            gastoState = gastoState.copy(viajeId = viajeState)
            listaGasto = listaGastos(viajeId)
        }
    }


    fun onGastoEvent(event: GastosEvent) {
        when (event) {

            is GastosEvent.OnDissmissDialog -> {
                muestraDialogoEliminacion = false
                muestraDialogoCreacion = false
            }

            is GastosEvent.onGastoGastoChanged -> {

                gastoState = gastoState.copy(gasto = event.gasto.toFloat())
            }

            is GastosEvent.onDescripcionGastoChanged -> {
                gastoState = gastoState.copy(descripcion = event.descripcion)
            }

            is GastosEvent.EliminaGasto -> {

                viewModelScope.launch {
                    Log.d("GastosViewModel", "EliminaGasto: ${event.id}")
                    var gastoABorrar = gastoRepository.getById(event.id.toString())!!
                    gastoRepository.delete(gastoABorrar)
                    listaGasto = listaGastos(viajeState.id)
                    muestraDialogoEliminacion = true
                }
            }

            is GastosEvent.CreaGasto -> {

                viewModelScope.launch {

                    if (gastoState.descripcion.isEmpty()) {
                        gastoState = gastoState.copy(
                            descripcion = "Gasto ${listaGasto.size + 1}",
                        )
                    }
                    gastoRepository.insert(gastoState.toGastos())
                    listaGasto = listaGastos(viajeState.id)
                    gastoState = gastoState.copy(
                        id = 0,
                        descripcion = "",
                        gasto = 0.0f
                    )

                    muestraDialogoCreacion = true
                }
            }
        }
    }
}

