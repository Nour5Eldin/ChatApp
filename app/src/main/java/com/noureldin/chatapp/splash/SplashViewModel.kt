package com.noureldin.chatapp.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.noureldin.chatapp.model.AppUser
import com.noureldin.chatapp.model.FirebaseUtils

class SplashViewModel : ViewModel() {
    val events = mutableStateOf<SplashViewEvent>(SplashViewEvent.Idle)
    val auth = Firebase.auth

    fun navigate(){
        if (auth.currentUser != null){
            getUserFromFireStore()
        }else{
            navigateToLogin()
        }

    }

    fun getUserFromFireStore(){
        FirebaseUtils.getUser(auth.currentUser?.uid!!, onSuccessListener = { docSnapshot->
             val user =  docSnapshot.toObject(AppUser::class.java)
            navigateToHome(user!!)
        }, onFailureListener = {
            navigateToLogin()
        })
    }
    fun navigateToHome(user: AppUser){
        events.value = SplashViewEvent.NavigateToHome(user)
    }
    fun navigateToLogin(){
       events.value = SplashViewEvent.NavigateToLogin

    }
}