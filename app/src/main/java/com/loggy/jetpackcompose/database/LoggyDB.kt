package com.loggy.jetpackcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loggy.jetpackcompose.domains.login.models.dao.UserDAO
import com.loggy.jetpackcompose.domains.login.models.tables.UserEntity

//Actualización de datos, cambiar la version de 1 a 2 , etc
@Database(entities = [UserEntity::class], version = 1)
abstract class LoggyDB : RoomDatabase(){
    abstract val dao: UserDAO
}