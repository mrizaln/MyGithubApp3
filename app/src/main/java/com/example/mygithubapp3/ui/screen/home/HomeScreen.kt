package com.example.mygithubapp3.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mygithubapp3.R
import com.example.mygithubapp3.di.Injection
import com.example.mygithubapp3.ui.ViewModelFactory
import com.example.mygithubapp3.ui.common.UiState
import com.example.mygithubapp3.ui.component.ErrorBox
import com.example.mygithubapp3.ui.component.ListContent
import com.example.mygithubapp3.ui.component.LoadingBox
import com.example.mygithubapp3.ui.theme.MyGithubApp3Theme
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    ),
    navigateToDetail: (username: String) -> Unit,
) {
    val initialQuery = "a"
    var query by remember { mutableStateOf("") }
    var queryJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.search(initialQuery)
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .semantics { isContainer = true }
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = {
                        query = it
                        if (query.isEmpty())
                            viewModel.search(initialQuery)
                    },
                    onSearch = {
                        if (queryJob != null) {
                            val job = queryJob!!
                            if (job.isActive) {
                                job.cancel()
                                Log.d("HomeScreen", "job cancelled")
                            }
                        }
                        queryJob = viewModel.search(query)
                        Log.d("HomeScreen", "running job. query: $query")

                    },
                    active = false,
                    onActiveChange = { },
                    placeholder = { Text(stringResource(R.string.search_hint)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = modifier
                        .align(Alignment.TopCenter)
                        .padding(bottom = 8.dp)
                ) {

                }
            }
        },
    ) { innerPadding ->
        viewModel.uiState.collectAsState(UiState.Loading).value.let { uiState ->
            when (uiState) {
                UiState.Loading    -> LoadingBox(modifier = modifier.fillMaxSize())
                is UiState.Error   -> ErrorBox(modifier = modifier.fillMaxSize(), message = uiState.errorMessage)
                is UiState.Success ->
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
                        modifier = modifier.padding(innerPadding)
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyGithubApp3Theme {
        HomeScreen(
            navigateToDetail = {}
        )
    }
}
