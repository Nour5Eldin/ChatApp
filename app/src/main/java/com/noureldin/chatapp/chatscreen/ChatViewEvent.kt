package com.noureldin.chatapp.chatscreen

sealed interface ChatViewEvent {
    data object Idle : ChatViewEvent
    data object NavigateBack : ChatViewEvent

}