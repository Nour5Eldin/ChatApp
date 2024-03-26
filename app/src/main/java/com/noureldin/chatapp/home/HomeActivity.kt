package com.noureldin.chatapp.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.R
import com.noureldin.chatapp.addRoom.AddRoomActivity
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.ui.theme.cyan
import com.noureldin.chatapp.ui.theme.white

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                HomeContent()
            }
        }
    }
}

@Composable
fun HomeContent(viewModel: HomeViewModel = viewModel()) {
    Scaffold(floatingActionButton ={
        FloatingActionButton(onClick = {viewModel.navigateToAddRoomScreen() }, containerColor = cyan, contentColor = white, modifier = Modifier.size(65.dp)) {
            Image(painter = painterResource(id = R.drawable.add), contentDescription = stringResource(
                R.string.add_new_room
            )
            )
        }
    }
    ) {paddingValues ->
        paddingValues
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            ) ) {

        }

    }
    TriggerEvents(viewModel.event.value)
}

@Composable
fun TriggerEvents(event: HomeViewEvent, viewModel: HomeViewModel= viewModel()) {
        val context = LocalContext.current
         when(event){
            HomeViewEvent.Idle -> {}
            HomeViewEvent.NavigateToAddRoomScreen -> {
                val intent = Intent(context, AddRoomActivity::class.java )
                context.startActivity(intent)
                viewModel.resetToIdleState()
            }
        }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    HomeContent()
}