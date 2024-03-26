package com.noureldin.chatapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppUser(
    val firstName: String?= null,
    val email : String? = null,
    val uid: String? = null
): Parcelable {
    companion object{
        val COLLECTION_NAME = "users"
    }
}