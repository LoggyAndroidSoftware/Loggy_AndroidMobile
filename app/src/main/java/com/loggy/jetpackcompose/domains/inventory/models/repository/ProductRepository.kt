package com.loggy.jetpackcompose.domains.inventory.models.repository


import com.loggy.jetpackcompose.domains.inventory.models.Product
import com.loggy.jetpackcompose.domains.inventory.models.dao.ProductDAO
import com.loggy.jetpackcompose.domains.inventory.models.tables.ProductEntity

class ProductRepository (private val ProductsDAO: ProductDAO){


    suspend fun getProducts(): List<Product>{
        val products = ProductsDAO.getProducts()
        return products.map {
            Product(
                id = it.id,
                name = it.name,
                brand = it.brand,
                stock = it.stock)
        }
    }
    suspend fun insertProduct(product: Product){
        ProductsDAO.insertProduct(
            ProductEntity(
            id = null,
            name = product.name,
            brand = product.brand,
            stock = product.stock
        )
        )
    }

    suspend fun deleteProduct(productId: Int){
        ProductsDAO.deleteProduct(productId)
    }

    suspend fun updateProduct(product: Product){
        ProductsDAO.updateProduct(
            ProductEntity(
                id = product.id,
                name = product.name,
                brand = product.brand,
                stock = product.stock
            )
        )
    }

    suspend fun getProductById(productId: Int): Product {
        val productEntity = ProductsDAO.getProductById(productId)
        return Product(
            id = productEntity.id,
            name = productEntity.name,
            brand = productEntity.brand,
            stock = productEntity.stock
        )
    }

}