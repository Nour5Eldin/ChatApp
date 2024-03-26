package com.noureldin.chatapp.splash

import com.noureldin.chatapp.model.AppUser


sealed interface SplashViewEvent {

    data object Idle : SplashViewEvent
    data class NavigateToHome(val user: AppUser) : SplashViewEvent
    data object NavigateToLogin : SplashViewEvent
}