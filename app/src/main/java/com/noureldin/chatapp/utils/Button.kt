package com.noureldin.chatapp.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noureldin.chatapp.R
import com.noureldin.chatapp.ui.theme.black30
import com.noureldin.chatapp.ui.theme.cyan
import com.noureldin.chatapp.ui.theme.gray
import com.noureldin.chatapp.ui.theme.white

@Composable
fun ChatAuthButton(title: String, onClickListener: () -> Unit, enabled: Boolean, modifier: Modifier=Modifier) {
    Button( modifier = if (enabled) modifier else modifier.shadow(spotColor = black30, shape = RoundedCornerShape(6.dp), elevation = 5.dp),
        onClick = { onClickListener() }, colors = ButtonDefaults.buttonColors(
        containerColor = if (enabled) cyan else white,
        contentColor = if (enabled) white else gray
    ), shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 32.dp)
    ) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.weight(1F))
        Image(painter = painterResource(id = if (enabled) R.drawable.forward else R.drawable.forward_black),
            contentDescription = stringResource(R.string.icon_forward)
        )
    }
    
}

@Composable
fun AddButton(modifier: Modifier, onClick: ()->Unit) {
Button(modifier = modifier, onClick ={ onClick()}, colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.White)) {
Text(text =  stringResource(R.string.create_room))
}
}

@Composable
fun SendButton(onClick: () -> Unit) {
    Button(onClick = { onClick() },shape = RoundedCornerShape(8.dp) ,colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.White)) {
        Text(text =  stringResource(id = R.string.send), fontSize = 12.sp)
        Image(painter = painterResource(id = R.drawable.send), contentDescription = stringResource(R.string.icon_send_messages)
        )
    }

}