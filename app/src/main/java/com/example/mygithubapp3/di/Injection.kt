package com.example.mygithubapp3.di

import android.content.Context
import com.example.mygithubapp3.data.UserRepository
import com.example.mygithubapp3.data.local.UserDatabase
import com.example.mygithubapp3.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val dao = UserDatabase.getInstance(context).userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}
