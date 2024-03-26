package com.noureldin.chatapp.login

import com.noureldin.chatapp.model.AppUser

sealed interface LoginViewEvent {
    data object Idle: LoginViewEvent
    data object NavigateToRegistration: LoginViewEvent
    data class NavigateToHome(val user: AppUser): LoginViewEvent
}