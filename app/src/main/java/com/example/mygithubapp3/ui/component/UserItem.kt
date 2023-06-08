package com.example.mygithubapp3.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mygithubapp3.data.Dummy
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    username: String,
    avatarUrl: String,
    isFavorite: Flow<Boolean>,
    onIconClick: (isFavorite: Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = username,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Text(
            text = username,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp)
        )

        isFavorite.collectAsState(false).value.let {
            IconButton(
                onClick = { onIconClick(it) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = if (it) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    MyGithubApp3Theme {
        val user = Dummy.userEntityList[0]
        UserItem(
            username = user.username,
            avatarUrl = user.avatarUrl,
            onIconClick = {},
            isFavorite = flowOf(true)
        )
    }
}
