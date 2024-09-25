package com.pmdm.travelmate.ui.features.newuser

import com.pmdm.travelmate.ui.features.newuser.newuserpassword.LoginPasswordUiState


data class NewUserUiState(

    val newUserPasswordUiState: LoginPasswordUiState = LoginPasswordUiState(),
)