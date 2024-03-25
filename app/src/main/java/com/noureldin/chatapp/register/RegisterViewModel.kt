package com.noureldin.chatapp.register

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class RegisterViewModel: ViewModel() {
    val firstNameState= mutableStateOf("")
    val firstNameErrorState = mutableStateOf<String?>(null)
    val emailState = mutableStateOf("")
    val EmailErrorState = mutableStateOf<String?>(null)
    val passwordState = mutableStateOf("")
    val PasswordErrorState = mutableStateOf<String?>(null)

    fun register(){
        if (validateFields()){

        }
    }
    fun validateFields(): Boolean{
        if (firstNameState.value.isEmpty() || firstNameState.value.isBlank()){
            firstNameErrorState.value = "Required"

            return false
        }else
            firstNameErrorState.value = null
        if (emailState.value.isEmpty() || emailState.value.isBlank()){
            EmailErrorState.value = "Required"
            return false
        }else
            EmailErrorState.value = null
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()){
            PasswordErrorState.value = "Required"
            return false
        }else
            PasswordErrorState.value = null
        if (!Patterns.EMAIL_ADDRESS.matcher(emailState.value).matches()){
            EmailErrorState.value = "Invalid Email Address"
            return false
        }else
            EmailErrorState.value = null
        if (passwordState.value.length < 6){
            PasswordErrorState.value = "password can't be less than 6 chars"
        }else
            PasswordErrorState.value = null
        return true
    }

}