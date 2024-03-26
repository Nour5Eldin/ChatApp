package com.noureldin.chatapp.register

import com.noureldin.chatapp.model.AppUser

sealed interface RegisterViewEvent {
    data object Idle : RegisterViewEvent
    data class NavigateToHome(val user: AppUser) : RegisterViewEvent
}