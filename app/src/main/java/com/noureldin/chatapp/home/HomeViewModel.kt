package com.noureldin.chatapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    val event = mutableStateOf<HomeViewEvent>(HomeViewEvent.Idle)

    fun navigateToAddRoomScreen(){
        event.value = HomeViewEvent.NavigateToAddRoomScreen
    }

    fun resetToIdleState(){
        event.value = HomeViewEvent.Idle
    }
}