package com.example.inventorymodule.components


import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loggy.jetpackcompose.domains.inventory.models.Product
import com.loggy.jetpackcompose.domains.inventory.models.repository.ProductRepository
import com.loggy.jetpackcompose.domains.inventory.views.utils.createPDF
import com.loggy.jetpackcompose.domains.inventory.views.utils.getOutputDirectory
import com.loggy.jetpackcompose.domains.inventory.views.utils.printPDF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(
    private val repository: ProductRepository
): ViewModel() {
    // Busqueda de productos
    var searchText by mutableStateOf("")
    private set

    var state by mutableStateOf(ProductState())
        private set

    // Cambia products a un MutableStateFlow
    private val _products = MutableStateFlow(listOf<Product>())
    // Crea un StateFlow público para exponer a la UI
    val products: StateFlow<List<Product>> = _products

    //Lista filtrada de Productos
    private val _filteredProducts = MutableStateFlow(listOf<Product>())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    fun updateSearchText(newText: String) {
        searchText = newText
        _filteredProducts.value = products.value.filter { it.name.contains(searchText, ignoreCase = true) }
    }
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
            _filteredProducts.value = repository.getProducts()
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
                _filteredProducts.value = _products.value.filter { it.name.contains(searchText, ignoreCase = true) }
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

    suspend fun printProducts(context: Context, products: List<Product>) {
        withContext(Dispatchers.IO) {
            // Crear el PDF
            val filePath = getOutputDirectory().absolutePath + "/myFile.pdf"
            createPDF(products, filePath)

            // Imprimir el PDF
            printPDF(context, filePath)
        }
    }
}