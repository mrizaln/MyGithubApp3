package com.example.mygithubapp3.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.semantics { isContainer = true }
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingBoxPreview() {
    MyGithubApp3Theme {
        LoadingBox()
    }
}
