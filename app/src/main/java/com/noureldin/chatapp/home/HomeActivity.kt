@file:OptIn(ExperimentalMaterial3Api::class)

package com.noureldin.chatapp.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.R
import com.noureldin.chatapp.addRoom.AddRoomActivity
import com.noureldin.chatapp.chatscreen.ChatActivity
import com.noureldin.chatapp.model.Category
import com.noureldin.chatapp.model.Constants
import com.noureldin.chatapp.model.Room
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.ui.theme.cyan
import com.noureldin.chatapp.ui.theme.white
import com.noureldin.chatapp.utils.ChatTopBar
import com.noureldin.chatapp.utils.LoadingDialog


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
    Scaffold(
        topBar = {
                 ChatTopBar(title = stringResource(id = R.string.chat_app))
        },


        floatingActionButton ={
        FloatingActionButton(onClick = {viewModel.navigateToAddRoomScreen() }, containerColor = cyan, contentColor = white, modifier = Modifier.size(65.dp)) {
            Image(painter = painterResource(id = R.drawable.add), contentDescription = stringResource(
                R.string.add_new_room
            )
            )
        }
    }
    ) {paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = paddingValues.calculateTopPadding())
            .padding(top = 32.dp)

        )
        {
            RoomsLazyGrid()
        }

    }
    TriggerEvents(viewModel.event.value)
    LoadingDialog(isLoading = viewModel.isLoading)
}


@Composable
fun RoomsLazyGrid(viewModel: HomeViewModel = viewModel()) {
    LaunchedEffect(Unit){
        viewModel.getRooms()
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2)){
        items(viewModel.roomsList.size){ position->
RoomCard(room = viewModel.roomsList[position])

        }
    }


}


@Composable
fun RoomCard(room: Room, viewModel: HomeViewModel= viewModel()) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.White ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.navigateToChatScreen(room)
            }
        ) {
            Image(
                painter = painterResource(
                    id = Category.fromId(
                        room.categoryID ?: Category.MUSICS
                    ).image ?: R.drawable.music
                ),
                contentDescription = stringResource(id = R.string.room_category_image),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(84.dp), contentScale = ContentScale.Crop
                )
            Text(text = room.name ?: "", fontSize = 14.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(10.dp))
        }


}
@Preview
@Composable
private fun CategoryPreview() {
    RoomCard(
        room = Room(
            Category.SPORTS,
            name = "Room Name",
            "Description",
            categoryID = Category.SPORTS
        )
    )
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

             is HomeViewEvent.NavigateToChatScreen -> {
                 val intent = Intent(context,ChatActivity::class.java)
                 intent.putExtra(Constants.ROOM_INTENT_EXTRA, event.room)
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