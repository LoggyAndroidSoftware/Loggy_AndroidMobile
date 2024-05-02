package com.loggy.jetpackcompose.domains.login.models.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("lastName")
    val lastName: String,
    @ColumnInfo("username")
    val username: String,
    @ColumnInfo("email")
    val email: String,
    @ColumnInfo("password")
    val password: String,
    @ColumnInfo("dni")
    val dni: String

)