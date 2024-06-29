package com.example.inventorymodule.components


import com.loggy.jetpackcompose.domains.inventory.models.Product


data class ProductState(
    val id: Int? = null,
    val name: String = "",
    val brand: String = "",
    val stock: Int = 0,
    val batch: String = "",
    val date: String = "",
    val productEntities: List<Product> = emptyList()
)