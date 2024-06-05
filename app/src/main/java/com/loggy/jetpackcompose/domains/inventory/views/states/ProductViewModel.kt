package com.example.inventorymodule.components


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

class ProductViewModel(
    private val repository: ProductRepository
): ViewModel() {

    var state by mutableStateOf(ProductState())
        private set

    // Cambia products a un MutableStateFlow
    private val _products = MutableStateFlow(listOf<Product>())
    // Crea un StateFlow público para exponer a la UI
    val products: StateFlow<List<Product>> = _products

    fun onNameChange(name: String, brand: String, stock: Int) {
        state = state.copy(
            name = name,
            brand = brand,
            stock = stock
        )
    }
    fun getProducts() {
        viewModelScope.launch {
            _products.value = repository.getProducts()
        }
    }

    fun insertProduct() {
        val product = Product(
            name = state.name,
            brand = state.brand,
            stock = state.stock
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
            }
        }
    }
    fun updateProduct() {
        val product = Product(
            id = state.id,
            name = state.name,
            brand = state.brand,
            stock = state.stock
        )
        viewModelScope.launch { repository.updateProduct(product) }
    }
    fun getProduct(productId: Int) {
        viewModelScope.launch {
            val product = repository.getProductById(productId)
            state = state.copy(
                id = product.id,
                name = product.name,
                brand = product.brand,
                stock = product.stock
            )
        }
    }
}