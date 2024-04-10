package com.noureldin.chatapp.home

import com.noureldin.chatapp.model.Room

sealed interface HomeViewEvent {
    data object Idle : HomeViewEvent
    data object NavigateToAddRoomScreen : HomeViewEvent
    data class NavigateToChatScreen(val room: Room): HomeViewEvent
}