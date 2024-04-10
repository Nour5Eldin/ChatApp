package com.noureldin.chatapp.model


import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.format.SignStyle

object FirebaseUtils {

    fun addUser(user: AppUser, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener){
    Firebase.firestore.collection(AppUser.COLLECTION_NAME)
        .document(user.uid!!)
        .set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

    }
    fun getUser(uid: String, onSuccessListener: OnSuccessListener<DocumentSnapshot> , onFailureListener: OnFailureListener){
        Firebase.firestore.collection(AppUser.COLLECTION_NAME)
            .document(uid)
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun addRoom(room : Room , onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener){
        val documentReference=Firebase.firestore.collection(Room.COLLECTION_NAME)
            .document()
            room.id = documentReference.id
             documentReference
            .set(room)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
    fun getRoom(onSuccessListener: OnSuccessListener<QuerySnapshot>,
                onFailureListener: OnFailureListener){
        Firebase.firestore.collection(Room.COLLECTION_NAME)
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

    }
    fun addMessage(message: Message,
                   onSuccessListener: OnSuccessListener<Void>,
                   onFailureListener: OnFailureListener){
        Firebase.firestore.collection(Room.COLLECTION_NAME).document(message.roomId!!)
            .collection(Message.COLLECTION_NAME).document()
            .set(message)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

    }
    fun getMessagesUpdate(roomId:String, eventListener: EventListener<QuerySnapshot>){
        Firebase.firestore.collection(Room.COLLECTION_NAME).document(roomId)
            .collection(Message.COLLECTION_NAME)
            .orderBy("dateTime", Query.Direction.DESCENDING)
            .addSnapshotListener(eventListener)
    }
}