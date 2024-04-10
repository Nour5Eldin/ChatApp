package com.noureldin.chatapp.register

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.noureldin.chatapp.model.AppUser
import com.noureldin.chatapp.model.DataUtils
import com.noureldin.chatapp.model.FirebaseUtils


class RegisterViewModel: ViewModel() {
    val firstNameState= mutableStateOf("")
    val firstNameErrorState = mutableStateOf<String?>(null)
    val emailState = mutableStateOf("")
    val emailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val passwordErrorState = mutableStateOf<String?>(null)
    val auth = Firebase.auth
    val event = mutableStateOf<RegisterViewEvent>(RegisterViewEvent.Idle)
    val isLoading = mutableStateOf(false)
    val messageState = mutableStateOf("")
    fun resetEventState(){
        event.value = RegisterViewEvent.Idle
    }
    fun register(){
        if (validateFields()){
            //Connect to firebase
            isLoading.value= true
            auth.createUserWithEmailAndPassword(emailState.value,passwordState.value)
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
                    addUserToFireStore(uid!!)
                }
        }
    }
    fun addUserToFireStore(uid: String){
        val user = AppUser(firstNameState.value, emailState.value, uid)
        FirebaseUtils.addUser(user, onSuccessListener = {
               isLoading.value = false
            DataUtils.appUser = user
            navigateToHome(user)
        }, onFailureListener = {
            isLoading.value = false
            messageState.value = it.message ?: ""
            Log.e("TAG","addUserToFireStore: ${it.message}")
        })
    }
    fun validateFields(): Boolean{
        if (firstNameState.value.isEmpty() || firstNameState.value.isBlank()){
            firstNameErrorState.value = "Required"

            return false
        }else
            firstNameErrorState.value = null
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
        event.value = RegisterViewEvent.NavigateToHome(user)
    }

}