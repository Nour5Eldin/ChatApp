package com.noureldin.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.black30
import com.noureldin.chatapp.ui.theme.cyan


@Composable
fun ChatAuthTextField(
    state: MutableState<String>,
    errorState: String?,
    lable: String,
    ispassword: Boolean = false,
    trailingIcon: Int? = null
){
    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = state.value, onValueChange = { newText ->
                state.value = newText
            }, modifier = Modifier.fillMaxWidth(0.95f), maxLines = 2,
            label = {
                Text(text = lable, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.Black)
            },
                colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Black,
                focusedIndicatorColor = cyan,
                errorIndicatorColor = Color.Red,
            ),
            visualTransformation = if (ispassword) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (trailingIcon !=null){
                    Image(painter = painterResource(id = R.drawable.check_mark), contentDescription = stringResource(
                        R.string.trailingicon_check_email
                    )
                    )
                }
            }
        )
        if (errorState !=null){
            Text(
                text = errorState,
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 2.dp)
                    .align(Alignment.Start)
            )
        }
    }

}
@Composable
fun ChatMessagingTextField(state: MutableState<String>, modifier: Modifier= Modifier) {
    OutlinedTextField(value = state.value, onValueChange = { state.value = it
    }, shape = RoundedCornerShape(topEnd = 12.dp), placeholder = { Text(text = stringResource(R.string.type_a_message), color = black30)},modifier=Modifier.fillMaxWidth(0.7F))

}
@Preview
@Composable
fun ChatMessagingPreview() {
    val variable = remember {
        mutableStateOf("")
    }
    ChatMessagingTextField(state = variable, modifier =Modifier )
}