package com.example.mygithubapp3.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun NothingBox(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text("Nothing to show")
        Text("¯\\_(ツ)_/¯")
    }
}

@Preview(showBackground = true)
@Composable
fun NothingBoxPreview() {
    MyGithubApp3Theme {
        NothingBox()
    }
}
