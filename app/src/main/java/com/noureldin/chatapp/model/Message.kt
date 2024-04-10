package com.noureldin.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val roomId: String? = null,
    val content: String? = null,
    val senderId: String? = null,
    val senderName: String? = null,
    val dateTime: Long? = null
) : Parcelable {
    companion object{
        val COLLECTION_NAME = "message"
    }
}
