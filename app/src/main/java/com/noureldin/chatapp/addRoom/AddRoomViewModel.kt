package com.noureldin.chatapp.addRoom

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.noureldin.chatapp.model.Category
import com.noureldin.chatapp.model.FirebaseUtils
import com.noureldin.chatapp.model.Room

class AddRoomViewModel: ViewModel() {
    val roomNameState = mutableStateOf("")
    val roomNameErrorState = mutableStateOf<String?>(null)
    val roomDescriptionState = mutableStateOf("")
    val roomDescriptionErrorState = mutableStateOf<String?>(null)
    val categoriesList = Category.getCategoriesList()
    val selectedCategoryItem =  mutableStateOf(categoriesList.get(0))
    val isExpandedDropDown = mutableStateOf(false)
    val isDone = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val event = mutableStateOf<AddRoomViewEvent>(AddRoomViewEvent.Idle)
    fun addRoom(){
        if (validateFields()){
            isLoading.value = true
            val room = Room(roomNameState.value, roomDescriptionState.value, selectedCategoryItem.value.id)
            FirebaseUtils.addRoom(room, onSuccessListener = {
                isLoading.value = false
                isDone.value = true
                navigateBack()
            }, onFailureListener = {
                isLoading.value = false
                Log.e("TAG","addRoom: ${it.message}")
            })
        }
    }

    fun navigateBack(){
        event.value = AddRoomViewEvent.NavigateBack
    }

    fun validateFields(): Boolean{
        if (roomNameState.value.isEmpty()|| roomNameState.value.isBlank()){
            roomNameErrorState.value = "Required"
           return false
        }else{
            roomNameErrorState.value = null
        }

        if (roomDescriptionState.value.isEmpty()|| roomDescriptionState.value.isBlank()){
            roomDescriptionErrorState.value = "Required"
            return false
        }else{
            roomDescriptionErrorState.value = null
        }

        return true
    }
}