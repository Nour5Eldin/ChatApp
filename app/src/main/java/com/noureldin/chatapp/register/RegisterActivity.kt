package com.noureldin.chatapp.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.utils.ChatAuthTextField
import com.noureldin.chatapp.utils.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.home.HomeActivity
import com.noureldin.chatapp.utils.ChatAuthButton
import com.noureldin.chatapp.utils.ErrorDialog
import com.noureldin.chatapp.utils.LoadingDialog

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                RegisterContent(onSuccessRegister = {
                      finishAffinity()
                }, onFinish = {
                    finish()
                })
            }
        }
    }
}

@Composable
fun RegisterContent( viewModel: RegisterViewModel= viewModel(),onFinish: ()-> Unit, onSuccessRegister: () -> Unit) {
    Scaffold(topBar = {
        ChatTopBar(title = stringResource(id = R.string.register)){
            onFinish()
        }
    }) {paddingValues ->
        paddingValues
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )) {
            Spacer(modifier = Modifier.fillMaxHeight(0.4F))
            ChatAuthTextField(state = viewModel.firstNameState , errorState = viewModel.firstNameErrorState.value , lable = stringResource(
                id = R.string.first_name
            ) )
            Spacer(modifier = Modifier.padding(4.dp))
            ChatAuthTextField(state = viewModel.emailState , errorState = viewModel.emailErrorState.value , lable = stringResource(
                id = R.string.email
            ) )
            Spacer(modifier = Modifier.padding(4.dp))
            ChatAuthTextField(state = viewModel.passwordState , errorState = viewModel.passwordErrorState.value , lable = stringResource(
                id = R.string.password
            ), ispassword = true)
            Spacer(modifier = Modifier.weight(1F))
            ChatAuthButton(title = stringResource(id = R.string.register), onClickListener = { viewModel.register() }, enabled = false, modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(32.dp))
        }
        TriggerEvent(event = viewModel.event.value){
            onSuccessRegister()
        }
        LoadingDialog(isLoading = viewModel.isLoading)
        ErrorDialog(title = viewModel.messageState)

    }
}

@Composable
fun TriggerEvent(event: RegisterViewEvent, viewModel: RegisterViewModel= viewModel(), onSuccessRegister: ()->Unit) {
    val context = LocalContext.current
    when(event){
        RegisterViewEvent.Idle -> {}
        is RegisterViewEvent.NavigateToHome -> {
            val intent = Intent(context,HomeActivity::class.java)
            context.startActivity(intent)
            onSuccessRegister()
            viewModel.resetEventState()
        }
    }
}
@Preview
@Composable
private fun RegisterPreview() {
    RegisterContent(onFinish = {}){}
}