package com.loggy.jetpackcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loggy.jetpackcompose.domains.inventory.models.dao.ProductDAO
import com.loggy.jetpackcompose.domains.inventory.models.tables.ProductEntity

//Actualización de datos, cambiar la version de 1 a 2 , etc
@Database(entities = [ProductEntity::class], version = 2)
abstract class ProductDB : RoomDatabase(){
    abstract val dao: ProductDAO
}