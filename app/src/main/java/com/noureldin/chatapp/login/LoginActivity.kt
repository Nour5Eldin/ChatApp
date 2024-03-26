package com.noureldin.chatapp.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.ui.theme.black30
import com.noureldin.chatapp.utils.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.home.HomeActivity
import com.noureldin.chatapp.register.RegisterActivity
import com.noureldin.chatapp.ui.theme.black50
import com.noureldin.chatapp.utils.ChatAuthButton
import com.noureldin.chatapp.utils.ChatAuthTextField
import com.noureldin.chatapp.utils.LoadingDialog

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                LoginContent{
                    finish()
                }
            }
        }
    }
}
@Composable
fun LoginContent(viewModel: LoginViewModel= viewModel(),onFinish: () -> Unit){
    Scaffold(topBar = {
        ChatTopBar(stringResource(R.string.login))
    }, modifier = Modifier.fillMaxSize()) { paddingValues ->
        paddingValues
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )) {
            Spacer(modifier = Modifier.fillMaxHeight(0.35f))
            Text(text = stringResource(R.string.welcome_back),
                color = black30,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))
            ChatAuthTextField(state = viewModel.emailState, viewModel.emailErrorState.value, stringResource(
                id = R.string.email)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            ChatAuthTextField(state = viewModel.passwordState, viewModel.passwordErrorState.value, stringResource(
                id = R.string.password) , ispassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChatAuthButton(title = stringResource(id = R.string.login), onClickListener = { viewModel.login() }, enabled = true, modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(4.dp))
            TextButton(onClick = { viewModel.navigateToRegister() }, modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = stringResource(R.string.or_create_an_account), color = black50, fontSize = 14.sp, fontWeight = FontWeight.Light)

            }
        }
    }
    TriggerEvent(event = viewModel.event.value){
        onFinish()
    }
    LoadingDialog(isLoading = viewModel.isLoading)
}

@Composable
fun TriggerEvent(event: LoginViewEvent, viewModel: LoginViewModel= viewModel(), onFinish: ()->Unit) {
    val context = LocalContext.current
    when(event){
        LoginViewEvent.Idle -> {}
        is LoginViewEvent.NavigateToHome -> {
            val intent = Intent(context,HomeActivity::class.java)
            context.startActivity(intent)
            viewModel.resetEventState()
            onFinish()
        }
        LoginViewEvent.NavigateToRegistration -> {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
            viewModel.resetEventState()
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginPreview(){
    LoginContent{}
}
