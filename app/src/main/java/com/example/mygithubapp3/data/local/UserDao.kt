package com.example.mygithubapp3.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mygithubapp3.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} ORDER BY username ASC")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<UserEntity>

    @Query("SELECT COUNT(1) FROM ${UserEntity.TABLE_NAME} WHERE username = :username LIMIT 1")
    fun isUserExists(username: String): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE username = :username")
    suspend fun deleteUserByUsername(username: String)

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    suspend fun deleteAll()
}
