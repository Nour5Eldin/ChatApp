package com.noureldin.chatapp.register

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.utils.ChatAuthTextField
import com.noureldin.chatapp.utils.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.utils.ChatAuthButton

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                RegisterContent{
                    finish()
                }
            }
        }
    }
}

@Composable
fun RegisterContent( viewModel: RegisterViewModel= viewModel(),onFinish: ()-> Unit) {
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
            ChatAuthTextField(state = viewModel.emailState , errorState = viewModel.EmailErrorState.value , lable = stringResource(
                id = R.string.email
            ) )
            Spacer(modifier = Modifier.padding(4.dp))
            ChatAuthTextField(state = viewModel.passwordState , errorState = viewModel.PasswordErrorState.value , lable = stringResource(
                id = R.string.password
            ) )
            Spacer(modifier = Modifier.weight(1F))
            ChatAuthButton(title = stringResource(id = R.string.register), onClickListener = {  }, enabled = false, modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(32.dp))
        }
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    RegisterContent{}
}