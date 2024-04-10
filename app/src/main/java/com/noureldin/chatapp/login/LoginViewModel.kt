package com.noureldin.chatapp.login

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.noureldin.chatapp.model.AppUser
import com.noureldin.chatapp.model.DataUtils
import com.noureldin.chatapp.model.FirebaseUtils


class LoginViewModel: ViewModel() {
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)
    val auth = Firebase.auth
    val event = mutableStateOf<LoginViewEvent>(LoginViewEvent.Idle)
    val isLoading = mutableStateOf(false)
    val messageState = mutableStateOf("")
    fun navigateToRegister(){
        event.value = LoginViewEvent.NavigateToRegistration
    }
    fun resetEventState(){
        event.value = LoginViewEvent.Idle
    }
    fun login(){
        if (validateFields()){
            //Connect to firebase
            isLoading.value = true
            auth.signInWithEmailAndPassword(emailState.value,passwordState.value)
                .addOnCompleteListener {task->
                    if (!task.isSuccessful){
                        Log.e("TAG","Error Occurred : ${task.exception?.message}")
                        isLoading.value = false
                        messageState.value = task.exception?.message ?: "Error Occurred"
                        return@addOnCompleteListener
                    }
                    val uid =  task.result.user?.uid
                    // Save User into Firebase Cloud  FireStore
                    // add Data to FireStore
                    getUserFromFireStore(uid!!)
                }
        }
    }
    fun getUserFromFireStore(uid: String){
        FirebaseUtils.getUser(uid, onSuccessListener = { docSnapshot->
            isLoading.value = false
            val user = docSnapshot.toObject(AppUser::class.java)
            DataUtils.appUser = user
            navigateToHome(user!!)
        }, onFailureListener = {
            isLoading.value = false
            messageState.value = it.message ?: ""
            Log.e("TAG","getUserFromFireStore: ${it.message}")
        })
    }
    fun validateFields(): Boolean{
        if (emailState.value.isEmpty() || emailState.value.isBlank()){
            emailErrorState.value = "Required"
            return false
        }else
            emailErrorState.value = null
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()){
            passwordErrorState.value = "Required"
            return false
        }else
            passwordErrorState.value = null
        if (!Patterns.EMAIL_ADDRESS.matcher(emailState.value).matches()){
            emailErrorState.value = "Invalid Email Address"
            return false
        }else
            emailErrorState.value = null
        if (passwordState.value.length < 6){
            passwordErrorState.value = "password can't be less than 6 chars"
        }else
            passwordErrorState.value = null
        return true
    }

    fun navigateToHome(user : AppUser){
        event.value = LoginViewEvent.NavigateToHome(user)
    }

}