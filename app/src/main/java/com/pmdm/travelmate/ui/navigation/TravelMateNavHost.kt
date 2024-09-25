import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import com.pmdm.travelmate.ui.features.gasto.GastosViewModel
import com.pmdm.travelmate.ui.features.inicio.InicioViewModel
import com.pmdm.travelmate.ui.features.inicio.updateuser.UpdateUserViewModel
import com.pmdm.travelmate.ui.features.login.LoginViewModel
import com.pmdm.travelmate.ui.features.login.resetpassword.ResetPasswordViewModel
import com.pmdm.travelmate.ui.features.lugar.LugarViewModel
import com.pmdm.travelmate.ui.features.lugar.muestralugares.MuestraLugaresViewModel
import com.pmdm.travelmate.ui.features.newuser.NewUserViewModel
import com.pmdm.travelmate.ui.features.updateviaje.UpdateViajeViewModel
import com.pmdm.travelmate.ui.navigation.HomeRoute
import com.pmdm.travelmate.ui.navigation.gastosRoute
import com.pmdm.travelmate.ui.navigation.inicioScreenRoute
import com.pmdm.travelmate.ui.navigation.loginScreenRoute
import com.pmdm.travelmate.ui.navigation.lugarRoute
import com.pmdm.travelmate.ui.navigation.mustraLugaresRoute
import com.pmdm.travelmate.ui.navigation.navigateToGastos
import com.pmdm.travelmate.ui.navigation.navigateToInicio
import com.pmdm.travelmate.ui.navigation.navigateToLogin
import com.pmdm.travelmate.ui.navigation.navigateToLugares
import com.pmdm.travelmate.ui.navigation.navigateToMuestraLugares
import com.pmdm.travelmate.ui.navigation.navigateToNewUser
import com.pmdm.travelmate.ui.navigation.navigateToUpdatePassword
import com.pmdm.travelmate.ui.navigation.navigateToUpdateUser
import com.pmdm.travelmate.ui.navigation.navigateToUpdateViaje
import com.pmdm.travelmate.ui.navigation.newUserScreenRoute
import com.pmdm.travelmate.ui.navigation.updatePasswordRoute
import com.pmdm.travelmate.ui.navigation.updateUserRoute
import com.pmdm.travelmate.ui.navigation.updateViajeRoute
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun TravelMateNavHost() {
    val navController = rememberNavController()
    val newUserViewModel: NewUserViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val inicioViewModel: InicioViewModel = hiltViewModel()
    val updatePasswordViewModel: ResetPasswordViewModel = hiltViewModel()
    val updateUserViewModel: UpdateUserViewModel = hiltViewModel()
    val updateViajeViewModel: UpdateViajeViewModel = hiltViewModel()
    val gastosViewModel: GastosViewModel = hiltViewModel()
    val lugaresViewModel: LugarViewModel = hiltViewModel()
    val muestraLugaresViewModel: MuestraLugaresViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Places.initialize(context, "")

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    )
    {
        loginScreenRoute(
            loginViewModel = loginViewModel,
//            navController = navController
            onNavigateToNewUser = {
                coroutineScope.launch {
                    // newUserViewModel.clearUsuario()
                    // newUserViewModel.esNuevoUsuario=true
                    navController.navigateToNewUser()
                    // loginViewModel.clearLogin()
                }
            },
            onNavigateToUpdate = {
                navController.navigateToUpdatePassword()
            },
            onNavigateToInicio = { correo ->
                inicioViewModel.iniciaUsuario(correo)
                inicioViewModel.iniciaEstadoNavBar()
                navController.navigateToInicio(correo)
            }
        )

        updateUserRoute(
            updateViewModel = updateUserViewModel,
            onClickAtras = {
                inicioViewModel.actualizaUsuario()
                inicioViewModel.restableceEstadoNavBar()
                navController.popBackStack()
            }
        )

        updateViajeRoute(
            updateViewModel = updateViajeViewModel,
            onClickAtras = {

                inicioViewModel.actualizaViajes()
                inicioViewModel.actualizaGastos()
                inicioViewModel.actualizaLugares()
                inicioViewModel.restableceEstadoNavBar()
                navController.popBackStack()
            },
            onNavigateToGastos = {
                gastosViewModel.inicializaGastos(updateViajeViewModel.viajeState.id)

                navController.navigateToGastos()
            },
            onNavigateToLugares = {
                lugaresViewModel.iniciaLugares(updateViajeViewModel.viajeState.id, context)

                navController.navigateToLugares()

            }
        )

        mustraLugaresRoute(
            muestraLugaresViewModel = muestraLugaresViewModel,
            onNavegarAtras = {
                lugaresViewModel.actualizaLugares()
                navController.popBackStack()

            }
        )
        lugarRoute(
            lugarViewModel = lugaresViewModel,
            onNavegarAtras = {
                inicioViewModel.actualizaViajes()
                inicioViewModel.actualizaGastos()
                inicioViewModel.actualizaLugares()
                updateViajeViewModel.numeroGastosLugaresPorViaje(updateViajeViewModel.viajeState.id)
                navController.popBackStack()
            },
            onNavigateToNewLugar = {

                muestraLugaresViewModel.inicializaLugares(
                    context,
                    updateViajeViewModel.viajeState,
                    lugaresViewModel.listaLugares
                )
                navController.navigateToMuestraLugares()
            }
        )

        gastosRoute(
            gastoViewModel = gastosViewModel,
            onNavegarAtras = {
                updateViajeViewModel.numeroGastosLugaresPorViaje(updateViajeViewModel.viajeState.id)
                inicioViewModel.actualizaViajes()
                inicioViewModel.actualizaGastos()
                navController.popBackStack()
            }
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            inicioScreenRoute(
                inicioViewModel = inicioViewModel,
                onNavegarAtras = {
                    navController.popBackStack()
                    loginViewModel.clearLogin()
                    loginViewModel.ocultaSnack()
                },
                onNavigateToUpdateUser = { id ->
                    coroutineScope.launch {
                        if (id != null) updateUserViewModel.inicializaUsuario(id)
                        navController.navigateToUpdateUser(id)
                    }
                },
                onNavigateToUpdateViaje = {
                    coroutineScope.launch {
                        if (it != null) updateViajeViewModel.inicializaViaje(it)
                        navController.navigateToUpdateViaje(it)
                    }
                }
            )
        }

        updatePasswordRoute(
            updateViewModel = updatePasswordViewModel,
            onClickAtras = {
                navController.popBackStack()
                newUserViewModel.clearUsuario()
            },
            onNavigateToLogin = { correo, navOptions ->
                if (correo != null) {
                    loginViewModel.iniciaUsuario(correo)
                    navController.navigateToLogin(correo, navOptions)
                }
            }
        )

        newUserScreenRoute(
            newUserViewModel = newUserViewModel,
            onClickAtras = {
                navController.popBackStack()
                newUserViewModel.clearUsuario()
            },
            onNavigateToLogin = { correo, navOptions ->
                if (correo != null) {
                    loginViewModel.iniciaUsuario(correo)
                    navController.navigateToLogin(correo, navOptions)
                }
                newUserViewModel.clearUsuario()
            })
    }

}

