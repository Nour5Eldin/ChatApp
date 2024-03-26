package com.noureldin.chatapp.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.R
import com.noureldin.chatapp.home.HomeActivity
import com.noureldin.chatapp.login.LoginActivity
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                SplashScreenContent{
                    finish()
                }
            }
        }
    }
}
@Composable
fun SplashScreenContent(viewModel: SplashViewModel = viewModel(),onFinish: () ->Unit){
    LaunchedEffect(key1 = Unit){
        Handler(Looper.getMainLooper()).postDelayed({
         viewModel.navigate()
        },2000)
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center) {
           // Spacer(modifier = Modifier.weight(1F))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.application_icon_image),
                modifier = Modifier.fillMaxHeight(0.20f),
                contentScale = ContentScale.Crop)
           // Spacer(modifier = Modifier.weight(1F))
            Image(painter = painterResource(id = R.drawable.signature),
                contentDescription = stringResource(R.string.application_signature),
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop)
        }
    TriggerEvents(event = viewModel.events.value){
        onFinish()
    }
    }

@Composable
fun TriggerEvents(event: SplashEvent ,viewModel: SplashViewModel = viewModel(),onFinish: () ->Unit){
    val context =   LocalContext.current
    when(event){
        SplashEvent.Idle -> {}
       is  SplashEvent.NavigateToHome -> {
            val intent = Intent(context, HomeActivity::class.java)
            val user = event.user
            intent.putExtra(Constants.USER_INTENT_EXTRA,user)
            context.startActivity(intent)
            onFinish()
        }
        SplashEvent.NavigateToLogin -> {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            onFinish()
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashPreview(){
    SplashScreenContent{

    }
}