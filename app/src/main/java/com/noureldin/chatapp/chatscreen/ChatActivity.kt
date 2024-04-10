package com.noureldin.chatapp.chatscreen

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.model.Constants
import com.noureldin.chatapp.model.Room
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.utils.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.model.DataUtils
import com.noureldin.chatapp.model.Message
import com.noureldin.chatapp.ui.theme.cyan
import com.noureldin.chatapp.ui.theme.green
import com.noureldin.chatapp.utils.ChatMessagingTextField
import com.noureldin.chatapp.utils.SendButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(Constants.ROOM_INTENT_EXTRA, Room::class.java)
                } else {
                    intent.getParcelableExtra(Constants.ROOM_INTENT_EXTRA)

                }
                ChatContent(room!!){
                    finish()
                }

            }
        }
    }
}

@Composable
fun ChatContent(room: Room, viewModel: ChatViewModel= viewModel(),onFinish: () -> Unit) {
    LaunchedEffect(key1 = Unit){
        viewModel.room = room
    }

    Scaffold(topBar = {ChatTopBar(title = room.name ?: "" ){
        viewModel.navigateBack()
    } }) {
        paddingValues ->
        paddingValues
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )) {
            ChatMessagesLazyColumn(modifier = Modifier
                .padding(24.dp)
                .fillMaxHeight(0.9F)
                .padding(top = paddingValues.calculateTopPadding())
                .background(
                    Color.White, RoundedCornerShape(20.dp)
                ))
            ChatMessagingBottomBar()
        }
    }
    TriggerEvents(event = viewModel.event.value) {
        onFinish()

    }
}

@Composable
fun ChatMessagingBottomBar(viewModel: ChatViewModel= viewModel()) {
    Row(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()) {
        ChatMessagingTextField(state = viewModel.messageState ,modifier = Modifier.fillMaxWidth(0.1F))
        Spacer(modifier = Modifier.width(2.dp))
        SendButton {
            viewModel.SendMessage()
        }
    }
}

@Composable
fun ChatMessagesLazyColumn(modifier: Modifier = Modifier, viewModel: ChatViewModel= viewModel()) {
    LaunchedEffect(key1 = Unit){
        viewModel.getMessages()
    }
    LazyColumn(modifier = modifier, reverseLayout = true ){
        items(viewModel.messageList.size){position->
            if (viewModel.messageList[position].senderId== DataUtils.appUser?.uid){
                SentMessageCard(message = viewModel.messageList[position])
            }else{

                ReceivedMessageCard(message = viewModel.messageList[position])

            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
fun formatDate(date:Date): String {
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return simpleDateFormat.format(date)
}
@Composable
fun SentMessageCard(message: Message) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        message.dateTime?.let {
            Text(text = formatDate(Date(it)), modifier = Modifier.align(Alignment.Bottom))

        }
        //Spacer(modifier = Modifier.width(4.dp))
        Text(text = message.content ?: "", fontSize = 14.sp, modifier = Modifier
            .background(
                cyan,
                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp)
            )
            .padding(14.dp)
            .padding(end = 4.dp),

        )


    }

}


@Preview
@Composable
fun SentMessagePreview() {
    SentMessageCard(message = Message(content = "Hello My friend ", dateTime = 8789799))
}

@Composable
fun ReceivedMessageCard(message: Message) {
    Column() {


        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = message.senderName ?: "",
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            Text(
                text = message.content ?: "", fontSize = 14.sp,
                modifier = Modifier
                    .background(
                        green,
                        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp)
                    )
                    .padding(14.dp)
                    .padding(end = 8.dp),

                )
            Spacer(modifier = Modifier.width(8.dp))
            message.dateTime?.let {
                Text(text = formatDate(Date(it)), modifier = Modifier.align(Alignment.Bottom))

            }


        }
    }
}

@Preview
@Composable
fun ReceivedMessageCardPreview() {
    ReceivedMessageCard(message = Message(content = "Hey How are you", senderName = "Sohaila", dateTime = 68939))
    
}

@Composable
fun TriggerEvents(event: ChatViewEvent, onFinish :() -> Unit) {
    when(event){
        ChatViewEvent.Idle -> {

        }
        ChatViewEvent.NavigateBack -> {
            onFinish()
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ChatPreview() {
    ChatContent(Room(name = "Chat Room")){

    }
}
