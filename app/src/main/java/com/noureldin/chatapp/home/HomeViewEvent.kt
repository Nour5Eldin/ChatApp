package com.noureldin.chatapp.home

sealed interface HomeViewEvent {
    data object Idle : HomeViewEvent
    data object NavigateToAddRoomScreen : HomeViewEvent
}