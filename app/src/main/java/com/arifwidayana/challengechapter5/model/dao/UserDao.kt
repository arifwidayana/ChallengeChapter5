package com.arifwidayana.challengechapter5.model.dao

import androidx.room.*
import com.arifwidayana.challengechapter5.model.data.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE username_user = :username")
    suspend fun getUsername(username: String): UserEntity

    @Update
    suspend fun updateProfileUser(user: UserEntity): Int

    @Delete
    suspend fun deleteUser(user: UserEntity): Int
}