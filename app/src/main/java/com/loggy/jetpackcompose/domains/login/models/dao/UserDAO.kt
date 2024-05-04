package com.loggy.jetpackcompose.domains.login.models.dao

import androidx.room.Dao
import androidx.room.Query
import com.loggy.jetpackcompose.domains.login.models.tables.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

}