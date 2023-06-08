package com.example.mygithubapp3.ui.screen.follow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygithubapp3.di.Injection
import com.example.mygithubapp3.model.UserDetail
import com.example.mygithubapp3.ui.ViewModelFactory
import com.example.mygithubapp3.ui.common.UiState
import com.example.mygithubapp3.ui.component.ErrorBox
import com.example.mygithubapp3.ui.component.ListContent
import com.example.mygithubapp3.ui.component.LoadingBox
import com.example.mygithubapp3.ui.component.NothingBox
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun FollowScreen(
    modifier: Modifier = Modifier,
    username: String,
    followType: UserDetail.FollowType,
    viewModel: FollowViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (username: String) -> Unit,
) {
    LaunchedEffect(username, followType) {
        viewModel.getFollow(username, followType)
    }
    viewModel.uiState.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            UiState.Loading    -> LoadingBox(modifier = modifier.fillMaxSize())
            is UiState.Error   -> ErrorBox(modifier = modifier.fillMaxSize())
            is UiState.Success -> {
                if (uiState.data.isEmpty())
                    NothingBox(modifier = modifier.fillMaxSize())
                else
                    ListContent(
                        userList = uiState.data,
                        navigateToDetail = navigateToDetail,
                        onIconClick = { isFavorite, user ->
                            if (isFavorite)
                                viewModel.removeUserFromFavorite(user)
                            else
                                viewModel.addUserToFavorite(user)
                        },
                        itemIsFavorite = { username ->
                            viewModel.isUserFavorited(username)
                        },
                        modifier = modifier
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowScreenPreview() {
    MyGithubApp3Theme {
        FollowScreen(
            username = "mrizaln",
            followType = UserDetail.FollowType.FOLLOWERS,
        ) {

        }
    }
}
