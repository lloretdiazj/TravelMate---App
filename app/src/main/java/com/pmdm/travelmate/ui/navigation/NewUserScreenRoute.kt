package com.pmdm.travelmate.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.newuser.NewUserScreen
import com.pmdm.travelmate.ui.features.newuser.NewUserViewModel


const val NewUserRoute = "newUser"

@RequiresApi(Build.VERSION_CODES.R)
fun NavGraphBuilder.newUserScreenRoute(
    newUserViewModel: NewUserViewModel,
    onClickAtras: () -> Unit,
    onNavigateToLogin: (correo: String, navOptions: NavOptions?) -> Unit
) {
    composable(route = NewUserRoute) {

        NewUserScreen(
            esNuevoClienteState = newUserViewModel.esNuevoUsuario,
            newUserUiState = newUserViewModel.newUserUiState,
            validacionNewUserUiState = newUserViewModel.validacionNewUserUiState,
            mostrarSnack = newUserViewModel.mostrarSnackState,
            mensaje = newUserViewModel.mensajeSnackBarState,
            onNewUserPasswordEvent = newUserViewModel::onNewUserPasswordEvent,
            onNavigateToLogin = onNavigateToLogin,
            onClickAtras = onClickAtras,
            onFotoCambiada = newUserViewModel::onFotoCambiada
        )
    }

}

fun NavController.navigateToNewUser() {
    this.navigate(NewUserRoute)

}

