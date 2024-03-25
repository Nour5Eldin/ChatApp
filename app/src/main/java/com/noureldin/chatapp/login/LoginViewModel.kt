package com.noureldin.chatapp.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)

    val event = mutableStateOf<LoginEvent>(LoginEvent.Idle)
    fun navigateToRegister(){
        event.value = LoginEvent.NavigateToRegistration
    }
    fun resetEventState(){
        event.value = LoginEvent.Idle
    }
}