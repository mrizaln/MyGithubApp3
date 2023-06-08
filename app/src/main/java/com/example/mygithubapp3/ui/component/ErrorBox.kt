package com.example.mygithubapp3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun ErrorBox(
    modifier: Modifier = Modifier,
    message: String? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text("There's an error")
        Text("(*_ _)人")

        message?.let {
            Spacer(Modifier.padding(16.dp))
            Text(
                text = "Error detail :",
                fontStyle = FontStyle.Italic
            )
            Text(
                message,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorBoxPreview() {
    MyGithubApp3Theme {
        ErrorBox(
            message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tortor ligula, tempor ac sem quis, tristique auctor massa. Phasellus dolor dolor, suscipit vitae ante ac, semper faucibus arcu. Donec eu aliquet nulla. Quisque id vulputate quam. Interdum et malesuada fames ac ante ipsum primis in faucibus. Cras ullamcorper purus eget nunc consectetur, ac mattis nulla imperdiet. Vivamus nec purus at justo eleifend cursus. Donec porta, magna nec luctus sagittis, lectus nunc rutrum lacus, id rhoncus libero lacus id justo. "
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorBoxPreviewEmpty() {
    MyGithubApp3Theme {
        ErrorBox()
    }
}
