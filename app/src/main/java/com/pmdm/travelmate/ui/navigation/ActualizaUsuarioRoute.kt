package com.pmdm.travelmate.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.inicio.updateuser.UpdateUserScreen
import com.pmdm.travelmate.ui.features.inicio.updateuser.UpdateUserViewModel

const val UpdateRoute = "updateUser?{id}"

@RequiresApi(Build.VERSION_CODES.R)
fun NavGraphBuilder.updateUserRoute(
    updateViewModel: UpdateUserViewModel,
    onClickAtras: () -> Unit,
) {
    composable(route = UpdateRoute) {


        UpdateUserScreen(
            usuarioInicioUiState = updateViewModel.usuarioUiState,
            nuevaContraseñaState = updateViewModel.nuevaContraseña,
            updateEvent = updateViewModel::onUpdateUserEvent,
            onNavegarAtras = onClickAtras,
            onFotoCambiada = updateViewModel::onFotoCambiada,
            validacionUpdate = updateViewModel.validacionUpdate,
            usuarioActualizado = updateViewModel.mostrarDialogoModificado
        )

    }

}

fun NavController.navigateToUpdateUser(id: Int, navOptions: NavOptions? = null) {

    val x = "$UpdateRoute/$id"
    this.navigate(x, navOptions)

}
