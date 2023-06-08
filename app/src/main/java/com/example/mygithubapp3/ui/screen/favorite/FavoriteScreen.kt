package com.example.mygithubapp3.ui.screen.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygithubapp3.di.Injection
import com.example.mygithubapp3.ui.ViewModelFactory
import com.example.mygithubapp3.ui.common.UiState
import com.example.mygithubapp3.ui.component.ListContent
import com.example.mygithubapp3.ui.component.LoadingBox
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (username: String) -> Unit,
) {
    LaunchedEffect(true) {
        viewModel.getUsers()
    }
    viewModel.uiState.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            UiState.Loading    -> LoadingBox(modifier = modifier.fillMaxSize())
            is UiState.Error   -> throw IllegalStateException("Impossible state: UiState.Error on database query")
            is UiState.Success ->
                ListContent(
                    userList = uiState.data,
                    navigateToDetail = navigateToDetail,
                    itemIsFavorite = { username ->
                        viewModel.isUserFavorited(username)
                    },
                    onIconClick = { isFavorite, user ->
                        if (isFavorite)
                            viewModel.removeUser(user.username)
                        else
                            viewModel.addUser(user)
                    },
                    modifier = modifier.fillMaxSize()
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    MyGithubApp3Theme {
        FavoriteScreen(
            navigateToDetail = {}
        )
    }
}
