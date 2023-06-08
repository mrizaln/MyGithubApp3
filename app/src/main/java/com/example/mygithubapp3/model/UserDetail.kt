package com.example.mygithubapp3.model

import androidx.annotation.StringRes
import com.example.mygithubapp3.R

data class UserDetail(
    val username: String,
    val fullName: String,
    val numOfFollowers: Int,
    val numOfFollowing: Int,
    val numOfRepository: Int,
    val avatarUrl: String,
    val location: String?
) {
    enum class FollowType(@StringRes val titleRes: Int) {
        FOLLOWERS(R.string.tab_followers),
        FOLLOWING(R.string.tab_following),;
    }
}
