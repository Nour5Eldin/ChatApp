package com.noureldin.chatapp.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {
    val events = mutableStateOf<SplashEvent>(SplashEvent.Idle)

    fun navigate(){
        navigateToLogin()
    }
    fun navigateToHome(){

    }

    fun navigateToLogin(){
       events.value = SplashEvent.NavigateToLogin

    }
}