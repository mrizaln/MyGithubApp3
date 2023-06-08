package com.example.mygithubapp3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.mygithubapp3.data.local.UserDao
import com.example.mygithubapp3.data.local.entity.UserEntity
import com.example.mygithubapp3.data.remote.RequestResult
import com.example.mygithubapp3.data.remote.retrofit.ApiService
import com.example.mygithubapp3.model.UserDetail

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) {
    companion object {
        const val TAG = "UserRepository"

        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(apiService: ApiService, userDao: UserDao): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = UserRepository(apiService, userDao)
                INSTANCE!!
            }
        }
    }

    fun getUsers(term: String): LiveData<RequestResult<List<UserEntity>>> = liveData {
        Log.d(TAG, "getUsers emit loading")
        emit(RequestResult.Loading)

        val data = MutableLiveData<RequestResult<List<UserEntity>>>()
        try {
            val response = apiService.getUsers(term)
            val users = (response.items ?: listOf())
                .map { userResponse ->
                    val username = userResponse?.login ?: ""
                    val avatarUrl = userResponse?.avatarUrl ?: ""
                    UserEntity(username, avatarUrl)
                }
            data.value = RequestResult.Success(users)
            Log.d(TAG, "getUsers emit success")
            emitSource(data)
        } catch (e: Exception) {
            Log.e(TAG, "getUsers error: $e (${e.message})")
            e.printStackTrace()
            emit(RequestResult.Error(e.message.toString()))
        }
    }

    fun getUserDetail(username: String): LiveData<RequestResult<UserDetail>> = liveData {
        emit(RequestResult.Loading)

        val data = MutableLiveData<RequestResult<UserDetail>>()
        try {
            val response = apiService.getUser(username)
            val userDetail = UserDetail(
                response.login ?: "",
                response.name ?: "",
                response.followers ?: 0,
                response.following ?: 0,
                response.publicRepos ?: 0,
                response.avatarUrl ?: "",
                response.location
            )
            data.value = RequestResult.Success(userDetail)
            emitSource(data)
        } catch (e: Exception) {
            Log.e(TAG, "getUserDetail error: $e (${e.message})")
            e.printStackTrace()
            emit(RequestResult.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<RequestResult<List<UserEntity>>> =
        getUsersFollow(username, UserDetail.FollowType.FOLLOWERS)

    fun getFollowing(username: String): LiveData<RequestResult<List<UserEntity>>> =
        getUsersFollow(username, UserDetail.FollowType.FOLLOWING)

    private fun getUsersFollow(
        username: String,
        follow: UserDetail.FollowType,
    ): LiveData<RequestResult<List<UserEntity>>> = liveData {
        emit(RequestResult.Loading)

        val data = MutableLiveData<RequestResult<List<UserEntity>>>()
        try {
            val response = when (follow) {
                UserDetail.FollowType.FOLLOWERS -> apiService.getFollowers(username)
                UserDetail.FollowType.FOLLOWING -> apiService.getFollowing(username)
            }
            val users = response.map { item ->
                UserEntity(item.login!!, item.avatarUrl ?: "")
            }
            data.value = RequestResult.Success(users)
            emitSource(data)
        } catch (e: Exception) {
            Log.e(TAG, "getUsersFollow error: $e (${e.message})")
            e.printStackTrace()
            emit(RequestResult.Error(e.message.toString()))
        }
    }

    // favorites
    fun isUserFavorited(username: String): LiveData<Boolean> = userDao.isUserExists(username)
    fun getFavoriteUsers(): LiveData<List<UserEntity>> = userDao.getUsers()
    fun getFavoriteUserByUsername(username: String): LiveData<UserEntity> = userDao.getUserByUsername(username)
    suspend fun removeFavoriteUsers() = userDao.deleteAll()
    suspend fun removeFavoriteUserByUsername(username: String) = userDao.deleteUserByUsername(username)
    suspend fun addUserToFavorite(user: UserEntity) = userDao.insertUser(user)
}
