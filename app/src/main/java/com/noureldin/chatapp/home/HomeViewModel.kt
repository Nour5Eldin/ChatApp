package com.noureldin.chatapp.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.noureldin.chatapp.model.FirebaseUtils
import com.noureldin.chatapp.model.Room

class HomeViewModel: ViewModel() {
    val event = mutableStateOf<HomeViewEvent>(HomeViewEvent.Idle)
    val roomsList = mutableStateListOf<Room>()
    val isLoading = mutableStateOf(false)
    fun getRooms(){
        isLoading.value = true
        FirebaseUtils.getRoom(
            onSuccessListener = {querySnapshot->
              isLoading.value = false
                querySnapshot.documents.forEach {
                    val room = it.toObject(Room::class.java)
                    roomsList.add(room!!)
                }


            },
            onFailureListener = {
                isLoading.value = false
                Log.e("TAG","Error Occurred : ${it.message}")
            }
        )
    }
    fun navigateToChatScreen(room: Room){
        event.value =  HomeViewEvent.NavigateToChatScreen(room)
    }
    fun navigateToAddRoomScreen(){
        event.value = HomeViewEvent.NavigateToAddRoomScreen
    }

    fun resetToIdleState(){
        event.value = HomeViewEvent.Idle
    }
}