package com.noureldin.chatapp.chatscreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.noureldin.chatapp.model.DataUtils
import com.noureldin.chatapp.model.FirebaseUtils
import com.noureldin.chatapp.model.Message
import com.noureldin.chatapp.model.Room
import java.util.Date

class ChatViewModel: ViewModel() {
    val event = mutableStateOf<ChatViewEvent>(ChatViewEvent.Idle)
    val messageState = mutableStateOf("")
    val messageList = mutableStateListOf<Message>()
    var room: Room? = null
    fun navigateBack(){
        event.value = ChatViewEvent.NavigateBack
    }
    fun SendMessage(){
        if (messageState.value.isEmpty() ||messageState.value.isBlank())return
        val message = Message(
            content = messageState.value,
            roomId = room?.id,
            dateTime = Date().time,
            senderId = DataUtils.appUser?.uid,
            senderName = DataUtils.appUser?.firstName
        )
        FirebaseUtils.addMessage(message, onSuccessListener = {
            messageState.value = ""
        }, onFailureListener = {
            Log.e("TAG","Error Occurred = ${it.message}")
        })
    }
    fun getMessages(){

        FirebaseUtils.getMessagesUpdate(room?.id!!, eventListener = {querySnapshot, exception ->
            if (exception !=null){
                Log.w("TAG","listen:error ${exception.message}")
                return@getMessagesUpdate
            }
            val mutableList= mutableListOf<Message>()
            for (dc in querySnapshot!!.documentChanges){
                when(dc.type){
                    DocumentChange.Type.ADDED -> {
                        mutableList.add(dc.document.toObject(Message::class.java))
                    }
                    DocumentChange.Type.MODIFIED -> {

                    }
                    DocumentChange.Type.REMOVED -> {

                    }
                }
            }
            mutableList.reverse()
            messageList.clear()
            messageList.addAll(mutableList)
        })

    }
}