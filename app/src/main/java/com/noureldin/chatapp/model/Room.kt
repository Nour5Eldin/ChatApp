package com.noureldin.chatapp.model

data class Room(
    val name: String? =null,
    val description: String? =null,
    val categoryID: String? = null
) {
    companion object{
        val COLLECTION_NAME = "Rooms"
    }
}
