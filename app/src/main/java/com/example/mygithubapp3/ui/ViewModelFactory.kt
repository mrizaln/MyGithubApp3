package com.example.mygithubapp3.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubapp3.data.UserRepository
import com.example.mygithubapp3.di.Injection
import com.example.mygithubapp3.ui.screen.about.AboutViewModel
import com.example.mygithubapp3.ui.screen.detail.DetailViewModel
import com.example.mygithubapp3.ui.screen.favorite.FavoriteViewModel
import com.example.mygithubapp3.ui.screen.follow.FollowViewModel
import com.example.mygithubapp3.ui.screen.home.HomeViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                INSTANCE!!
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // @formatter:off
            modelClass.isAssignableFrom(HomeViewModel::class.java)     -> HomeViewModel(userRepository)
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(userRepository)
            modelClass.isAssignableFrom(AboutViewModel::class.java)    -> AboutViewModel(userRepository)
            modelClass.isAssignableFrom(DetailViewModel::class.java)   -> DetailViewModel(userRepository)
            modelClass.isAssignableFrom(FollowViewModel::class.java)   -> FollowViewModel(userRepository)
            else -> throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            // @formatter:on
        } as T
    }
}
