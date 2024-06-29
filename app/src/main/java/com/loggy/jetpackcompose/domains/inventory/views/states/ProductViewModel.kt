package com.example.inventorymodule.components


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loggy.jetpackcompose.domains.inventory.models.Product
import com.loggy.jetpackcompose.domains.inventory.models.repository.ProductRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductViewModel(
    private val repository: ProductRepository
): ViewModel() {
    // Busqueda de productos
    var searchText by mutableStateOf("")
    private set
    // Filtros de busqueda
    var searchFilters by mutableStateOf(List(3) { index -> index == 0 })
    var state by mutableStateOf(ProductState())
        private set

    // Cambia products a un MutableStateFlow
    private val _products = MutableStateFlow(listOf<Product>())
    // Crea un StateFlow público para exponer a la UI
    val products: StateFlow<List<Product>> = _products

    //Lista filtrada de Productos
    private val _filteredProducts = MutableStateFlow(listOf<Product>())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts
    fun updateFilters(searchText: String, searchByName: Boolean, searchByBrand: Boolean, searchByStock: Boolean) {
        this.searchText = searchText
        val searchTextTrimmed = searchText.trim()
        _filteredProducts.value = products.value.filter { product ->
            (searchByName && product.codename.contains(searchText, ignoreCase = true)) ||
                    (searchByBrand && product.brand.contains(searchTextTrimmed, ignoreCase = true)) ||
                    (searchByStock && product.stock.toString().contains(searchTextTrimmed))
        }
    }
    fun updateSearchText(newText: String) {
        searchText = newText
        _filteredProducts.value = products.value.filter { it.codename.contains(searchText, ignoreCase = true) }
    }
    fun onNameChange(name: String, brand: String, stock: Int, batch: String) {
        state = state.copy(
            name = name,
            brand = brand,
            stock = stock,
            batch = batch
        )
    }
    fun getProducts() {
        viewModelScope.launch {
            _products.value = repository.getProducts()
            _filteredProducts.value = repository.getProducts()
        }
    }

    fun insertProduct() {
        val product = Product(
            codename = state.name,
            brand = state.brand,
            stock = state.stock,
            batch = state.batch,
            date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
        )
        viewModelScope.launch { repository.insertProduct(product) }

    }

    var showSnackbar by mutableStateOf(false)

    fun resetState() {
        state = ProductState()
    }

    fun deleteProduct(index: Int) {
        viewModelScope.launch {
            val product = _products.value[index]
            product.id?.let {
                repository.deleteProduct(it)

                // Actualiza la lista de productos
                _products.value = repository.getProducts()
                _filteredProducts.value = _products.value.filter { it.codename.contains(searchText, ignoreCase = true) }
            }
        }
    }
    fun updateProduct() {
        val product = Product(
            id = state.id,
            codename = state.name,
            brand = state.brand,
            stock = state.stock,
            batch = state.batch,
            date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        )
        viewModelScope.launch { repository.updateProduct(product) }
    }
    fun getProduct(productId: Int) {
        viewModelScope.launch {
            val product = repository.getProductById(productId)
            state = state.copy(
                id = product.id,
                name = product.codename,
                brand = product.brand,
                stock = product.stock,
                batch = product.batch,
                date = product.date
            )
        }
    }


}