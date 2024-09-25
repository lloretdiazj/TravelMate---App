package com.pmdm.travelmate.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.login.resetpassword.ResetPasswordScreen
import com.pmdm.travelmate.ui.features.login.resetpassword.ResetPasswordViewModel


const val UpdatePassRoute = "updatePassUser"

fun NavGraphBuilder.updatePasswordRoute(
    updateViewModel: ResetPasswordViewModel,
    onClickAtras: () -> Unit,
    onNavigateToLogin: (correo: String, navOptions: NavOptions?) -> Unit
) {
    composable(route = UpdatePassRoute) {


        ResetPasswordScreen(
            email = updateViewModel.emailIntroducido,
            nuevaContraseña = updateViewModel.contraseñaIntroducida,
            codigoNuevo = updateViewModel.codigoIntroducido,
            mostrarSnack = updateViewModel.mostrarSnack,
            mensaje = updateViewModel.mensajeSnack,
            esCodigoEnviado = updateViewModel.esCodigoEnviado,
            onClickAtras = onClickAtras,
            onNavigateToLogin = onNavigateToLogin,
            validacionLoginUiState = updateViewModel.validacionLoginUiState,
            onResetPassworEvent = updateViewModel::onResetPasswordEvent,
        )
    }

}

fun NavController.navigateToUpdatePassword() {
    this.navigate(UpdatePassRoute)

}

