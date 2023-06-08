package com.example.mygithubapp3.ui.screen.detail

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mygithubapp3.R
import com.example.mygithubapp3.data.Dummy
import com.example.mygithubapp3.data.local.entity.UserEntity
import com.example.mygithubapp3.di.Injection
import com.example.mygithubapp3.model.UserDetail
import com.example.mygithubapp3.ui.ViewModelFactory
import com.example.mygithubapp3.ui.common.UiState
import com.example.mygithubapp3.ui.component.ErrorBox
import com.example.mygithubapp3.ui.component.LoadingBox
import com.example.mygithubapp3.ui.screen.follow.FollowScreen
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    username: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (username: String) -> Unit,
) {
    LaunchedEffect(username) {
        viewModel.getUserDetail(username)
    }
    viewModel.uiState.collectAsState(UiState.Loading).value.let { uiState ->
        Log.d("DetailScreen", "data loaded: $uiState")
        when (uiState) {
            UiState.Loading    -> LoadingBox(modifier = modifier.fillMaxSize())
            is UiState.Error   -> ErrorBox(modifier = modifier.fillMaxSize(), message = uiState.errorMessage)
            is UiState.Success -> {
                val userDetail = uiState.data
                val userEntity = UserEntity(username = userDetail.username, avatarUrl = userDetail.avatarUrl)
                DetailContent(
                    modifier = modifier.fillMaxSize(),
                    userDetail = userDetail,
                    isFavorite = viewModel.isUserFavorited(userDetail.username),
                    onIconClick = { isFavorite ->
                        if (isFavorite)
                            viewModel.removeUserFromFavorite(userEntity)
                        else
                            viewModel.addUserToFavorite(userEntity)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    userDetail: UserDetail,
    isFavorite: Flow<Boolean>,
    onIconClick: (isFavorite: Boolean) -> Unit,
    navigateToDetail: (username: String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        UserBasicInformationContent(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            userDetail = userDetail,
            isFavorite = isFavorite,
            onIconClick = onIconClick
        )
        UserFollowInformationContent(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            username = userDetail.username,
            navigateToDetail = navigateToDetail,
        )
    }
}

@Composable
private fun UserBasicInformationContent(
    modifier: Modifier = Modifier,
    userDetail: UserDetail,
    isFavorite: Flow<Boolean>,
    onIconClick: (isFavorite: Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .weight(10f)
                .padding(8.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = userDetail.avatarUrl,
                contentDescription = userDetail.username,
                modifier = Modifier
                    .clip(CircleShape),
            )
            isFavorite.collectAsState(false).value.let {
                IconButton(
                    onClick = { onIconClick(it) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (it) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                    )
                }
            }

        }
        Text(
            text = userDetail.fullName,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Typeface.SANS_SERIF),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = userDetail.username,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.weight(1f)
        )
        userDetail.location?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location")
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.List, contentDescription = null)
            Text(
                text = stringResource(R.string.public_repos_num, userDetail.numOfRepository),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = stringResource(R.string.followers, userDetail.numOfFollowers),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = stringResource(R.string.following, userDetail.numOfFollowing),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun UserFollowInformationContent(
    modifier: Modifier = Modifier,
    username: String,
    navigateToDetail: (username: String) -> Unit,
) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = UserDetail.FollowType.values()

    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabs.forEachIndexed { index, followType ->
                Tab(
                    text = { Text(stringResource(followType.titleRes)) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }
        FollowScreen(
            username = username,
            followType = tabs[tabIndex],
            navigateToDetail = navigateToDetail
        )
    }
}

//------------------------------------------[ preview ]---------------------------------------------

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    MyGithubApp3Theme {
        val user = Dummy.userDetail
        DetailContent(
            userDetail = user,
            isFavorite = flowOf(true),
            onIconClick = {},
            navigateToDetail = {}
        )
    }
}
