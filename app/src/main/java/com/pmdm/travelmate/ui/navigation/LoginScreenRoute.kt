package com.pmdm.travelmate.ui.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pmdm.travelmate.ui.features.login.LoginScreen
import com.pmdm.travelmate.ui.features.login.LoginViewModel


const val HomeRoute = "login?{correo}"

fun NavGraphBuilder.loginScreenRoute(
    loginViewModel: LoginViewModel,
    onNavigateToNewUser: () -> Unit,
    onNavigateToUpdate: () -> Unit,
    onNavigateToInicio: (correo: String) -> Unit,
) {

    composable(route = HomeRoute) { backStackEntry ->

        LoginScreen(
            usuarioUiState = loginViewModel.usuarioUiState,
            validacionLoginUiState = loginViewModel.validacionLoginUiState,
            onLoginEvent = loginViewModel::onLoginEvent,
            mostrarSnack = loginViewModel.mostrarSnackBar,
            onMostrarSnackBar = loginViewModel.onMostrarSnackBar,
            onNavigateToUpdatePass = onNavigateToUpdate,
            onNavigateToInicio = onNavigateToInicio,
            onNavigateToNewUser = onNavigateToNewUser

        )
    }
}

fun NavController.navigateToLogin(valorLogin: String, navOptions: NavOptions? = null) {
    val x = "$HomeRoute/$valorLogin"
    this.navigate(x, navOptions)
}

//val ruta = "$PantallaBRoute/$nombre"
//Log.d("Navegacion", "Navegando a $ruta")
//this.navigate(ruta, navOptions)
//}