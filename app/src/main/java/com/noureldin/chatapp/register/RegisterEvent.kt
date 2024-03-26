package com.noureldin.chatapp.register

import com.noureldin.chatapp.model.AppUser

sealed interface RegisterEvent {
    data object Idle : RegisterEvent
    data class NavigateToHome(val user: AppUser) : RegisterEvent
}