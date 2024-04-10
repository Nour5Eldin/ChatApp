package com.noureldin.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    var id: String?=null,
    val name: String? =null,
    val description: String? =null,
    val categoryID: String? = null
): Parcelable {
    companion object{
        val COLLECTION_NAME = "Rooms"
    }
}
