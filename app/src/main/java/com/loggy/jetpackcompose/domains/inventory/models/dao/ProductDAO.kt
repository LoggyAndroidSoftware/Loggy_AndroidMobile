package com.loggy.jetpackcompose.domains.inventory.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.loggy.jetpackcompose.domains.inventory.models.tables.ProductEntity

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProduct(productId: Int)

}