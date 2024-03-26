package com.noureldin.chatapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.cyan
import com.noureldin.chatapp.ui.theme.white


@Composable
fun LoadingDialog(isLoading: MutableState<Boolean>) {
    if (isLoading.value){
        Dialog(onDismissRequest = { isLoading.value = false}) {
            CircularProgressIndicator(color = cyan, modifier = Modifier
                .background(
                    white,
                    RoundedCornerShape(8.dp)
                )
                .padding(36.dp)
                )
        }
    }
}


@Composable
fun ErrorDialog(title: MutableState<String>) {
    if ( title.value.isNotEmpty()){
       AlertDialog(onDismissRequest =  {
              title.value = ""
        },
            confirmButton = {
                TextButton(onClick = {title.value = "" }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }, text = {
                Text(text =title.value)
           })
    }


}