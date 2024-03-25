package com.noureldin.chatapp.login

sealed interface LoginEvent {
    data object Idle: LoginEvent
    data object NavigateToRegistration: LoginEvent
    data object NavigateToHome: LoginEvent
}