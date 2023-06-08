package com.example.mygithubapp3.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.mygithubapp3.data.UserRepository
import com.example.mygithubapp3.data.local.entity.UserEntity
import com.example.mygithubapp3.data.remote.RequestResult
import com.example.mygithubapp3.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<UserEntity>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserEntity>>> = _uiState

    fun search(name: String) = viewModelScope.launch {
        userRepository.getUsers(name).asFlow().collect { result ->
            Log.d("HomeViewModel", "result: $result")
            when (result) {
                RequestResult.Loading  -> _uiState.value = UiState.Loading
                is RequestResult.Error -> _uiState.value = UiState.Error(result.error)
                is RequestResult.Success -> _uiState.value = UiState.Success(result.data)
            }
        }
    }

    fun isUserFavorited(username: String) = userRepository.isUserFavorited(username).asFlow()

    fun addUserToFavorite(user: UserEntity) =
        viewModelScope.launch { userRepository.addUserToFavorite(user) }

    fun removeUserFromFavorite(user: UserEntity) =
        viewModelScope.launch { userRepository.removeFavoriteUserByUsername(user.username) }
}
