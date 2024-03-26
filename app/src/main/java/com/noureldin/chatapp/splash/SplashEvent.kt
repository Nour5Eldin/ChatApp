package com.noureldin.chatapp.splash

import com.noureldin.chatapp.model.AppUser


sealed interface SplashEvent {

    data object Idle : SplashEvent
    data class NavigateToHome(val user: AppUser) : SplashEvent
    data object NavigateToLogin : SplashEvent
}