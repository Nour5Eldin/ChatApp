package com.noureldin.chatapp.addRoom

import android.hardware.TriggerEvent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.ChatAppTheme
import com.noureldin.chatapp.ui.theme.black38
import com.noureldin.chatapp.utils.AddButton
import com.noureldin.chatapp.utils.ChatAuthTextField
import com.noureldin.chatapp.utils.ChatTopBar
import com.noureldin.chatapp.utils.LoadingDialog

class AddRoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                AddRoomContent{
                    finish()
                }
            }
        }
    }
}


@Composable
fun AddRoomContent(viewModel: AddRoomViewModel= viewModel(), onFinish:() -> Unit) {
    Scaffold(topBar = {
        ChatTopBar(title = stringResource(R.string.chat_app)){
            onFinish()
        }

    }) { paddingValues ->
        paddingValues
        Column(modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = paddingValues.calculateTopPadding()), horizontalAlignment = Alignment.CenterHorizontally){
            Card(colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 28.dp)
                    .fillMaxWidth()) {
                Text(text = stringResource(R.string.create_new_room),
                    color = black38,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),

                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(painter = painterResource(id = R.drawable.addroom),
                    contentDescription = stringResource(R.string.add_new_room_image),
                    modifier = Modifier
                        .fillMaxHeight(0.1f)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop )
                Spacer(modifier = Modifier.height(12.dp))
                ChatAuthTextField(state = viewModel.roomNameState ,
                    errorState = viewModel.roomNameErrorState.value ,
                    lable = stringResource(R.string.room_name) )
                Spacer(modifier = Modifier.height(12.dp))
                CategoryDropDown(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(12.dp))
                ChatAuthTextField(state = viewModel.roomDescriptionState ,
                    errorState = viewModel.roomDescriptionErrorState.value ,
                    lable = stringResource(R.string.room_description) )
                Spacer(modifier = Modifier.height(60.dp))
                AddButton(modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .align(Alignment.CenterHorizontally)) {
                    viewModel.addRoom()
                }
            }
        }
        LoadingDialog(isLoading = viewModel.isLoading)
        TriggerEvents( viewModel.event.value){
            onFinish()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(viewModel: AddRoomViewModel = viewModel(), modifier: Modifier) {
    ExposedDropdownMenuBox(
        expanded = viewModel.isExpandedDropDown.value,
        onExpandedChange = {
            viewModel.isExpandedDropDown.value = !viewModel.isExpandedDropDown.value
        },
        modifier = modifier

    ) {
        OutlinedTextField(
            value = viewModel.selectedCategoryItem.value.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = stringResource(R.string.room_category),

                    )

            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isExpandedDropDown.value)

            },
            leadingIcon = {
                Image(painter = painterResource(
                    id = viewModel.selectedCategoryItem.value.image ?: R.drawable.music
                ), contentDescription = stringResource(R.string.room_category_image),
                    modifier = Modifier.size(35.dp)
                )
            },
            modifier= Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = viewModel.isExpandedDropDown.value,
            onDismissRequest = { viewModel.isExpandedDropDown.value = false}) {
            viewModel.categoriesList.forEach {category->
                DropdownMenuItem(text = { Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = category.image ?: R.drawable.music), contentDescription = "",
                        modifier= Modifier.size(35.dp)
                         )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = category.name ?: "")
                }  }, onClick = {
                    viewModel.selectedCategoryItem.value = category
                    viewModel.isExpandedDropDown.value =false
                })
            }
        }
    }

}

@Composable
fun TriggerEvents(event: AddRoomViewEvent, viewModel: AddRoomViewModel= viewModel(), onFinish: () -> Unit) {
    when(event){
        AddRoomViewEvent.Idle -> {}
        AddRoomViewEvent.NavigateBack -> {
            onFinish()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddRoomPreview() {
    AddRoomContent{

    }
}