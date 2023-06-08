package com.example.mygithubapp3.data.remote.retrofit

import com.example.mygithubapp3.data.remote.response.UserDetailResponse
import com.example.mygithubapp3.data.remote.response.UserItemResponse
import com.example.mygithubapp3.data.remote.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") query: String
    ) : UserSearchResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ) : UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ) : List<UserItemResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ) : List<UserItemResponse>
}
