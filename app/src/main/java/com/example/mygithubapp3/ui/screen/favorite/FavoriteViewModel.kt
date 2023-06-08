package com.example.mygithubapp3.ui.screen.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.mygithubapp3.data.UserRepository
import com.example.mygithubapp3.data.local.entity.UserEntity
import com.example.mygithubapp3.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<UserEntity>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<UserEntity>>> = _uiState

    fun getUsers() = viewModelScope.launch {
        userRepository.getFavoriteUsers().asFlow().collect { result ->
            Log.d("FavoriteViewModel", "result: $result")
            _uiState.value = UiState.Success(result)
        }
    }

    fun removeUsers() = viewModelScope.launch { userRepository.removeFavoriteUsers() }

    fun isUserFavorited(username: String) = userRepository.isUserFavorited(username).asFlow()

    fun addUser(user: UserEntity) = viewModelScope.launch { userRepository.addUserToFavorite(user) }

    fun removeUser(username: String) = viewModelScope.launch { userRepository.removeFavoriteUserByUsername(username) }
}
