package com.noureldin.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.cyan


@Composable
fun ChatAuthTextField(state: MutableState<String>, errorState: String?, lable: String, trailingIcon: Int?=null){
    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = state.value, onValueChange = { newText ->
                state.value = newText
            }, modifier = Modifier.fillMaxWidth(0.9f),
            label = {
                Text(text = lable, fontSize = 12.sp, fontWeight = FontWeight.Normal)
            },
                colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Gray,
                focusedIndicatorColor = cyan,
                errorIndicatorColor = Color.Red
            ),
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
                fontWeight = FontWeight.Normal
            )
        }
    }

}