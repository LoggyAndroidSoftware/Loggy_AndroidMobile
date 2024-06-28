package com.loggy.jetpackcompose.domains.inventory.models.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("brand")
    val brand:String,
    @ColumnInfo("stock")
    val stock: Int,
    @ColumnInfo("batch")
    val batch: String,
    @ColumnInfo("date")
    val date: String

)
