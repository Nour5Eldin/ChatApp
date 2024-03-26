package com.noureldin.chatapp.addRoom

sealed interface AddRoomViewEvent {
    data object Idle : AddRoomViewEvent
    data object NavigateBack : AddRoomViewEvent

}