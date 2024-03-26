package com.noureldin.chatapp.login

import com.noureldin.chatapp.model.AppUser

sealed interface LoginEvent {
    data object Idle: LoginEvent
    data object NavigateToRegistration: LoginEvent
    data class NavigateToHome(val user: AppUser): LoginEvent
}