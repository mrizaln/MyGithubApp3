package com.example.mygithubapp3.data

import com.example.mygithubapp3.data.local.entity.UserEntity
import com.example.mygithubapp3.model.UserDetail

object Dummy {
    val userEntityList = listOf(
        UserEntity(
            "mrizaln",
            "https://avatars.githubusercontent.com/u/61090869?v=4"
        ),
        UserEntity(
            "torvalds",
            "https://avatars.githubusercontent.com/u/1024025?v=4"
        ),
        UserEntity(
            "CyanNyan",
            "https://avatars.githubusercontent.com/u/75556638?v=4"
        )
    )

    val userDetail = UserDetail(
        username = "mrizaln",
        fullName = "Muhammad Rizal Nurromdhoni",
        numOfFollowers = 3,
        numOfFollowing = 8,
        numOfRepository = 31,
        avatarUrl = "https://avatars.githubusercontent.com/u/61090869?v=4",
        location = "Indonesia"
    )
}
