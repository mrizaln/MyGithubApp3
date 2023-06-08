package com.example.mygithubapp3.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mygithubapp3.data.Dummy
import com.example.mygithubapp3.data.local.entity.UserEntity
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    userList: List<UserEntity>,
    itemIsFavorite: (username: String) -> Flow<Boolean>,
    onIconClick: (isFavorite: Boolean, user: UserEntity) -> Unit,
    navigateToDetail: (username: String) -> Unit,
) {
    if (userList.isEmpty())
        NothingBox(modifier = modifier)
    else
        LazyColumn(
            modifier = modifier
        ) {
            items(userList, key = { it.username }) { user ->
                UserItem(
                    username = user.username,
                    avatarUrl = user.avatarUrl,
                    isFavorite = itemIsFavorite(user.username),
                    onIconClick = { onIconClick(it, user) },
                    modifier = Modifier.clickable {
                        navigateToDetail(user.username)
                    }
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
fun ListContentPreview() {
    MyGithubApp3Theme {
        val list = mutableListOf<UserEntity>().apply {
            repeat(5) { addAll(Dummy.userEntityList) }
        }
        ListContent(
            modifier = Modifier.fillMaxSize(),
            userList = list,
            itemIsFavorite = { flowOf(Random.nextBoolean()) },
            onIconClick = { _, _ -> },
            navigateToDetail = {}
        )
    }
}
