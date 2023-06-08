package com.example.mygithubapp3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
class UserEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey(autoGenerate = false)
    val username: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
) {
    companion object {
        const val TABLE_NAME = "user"
    }
}
