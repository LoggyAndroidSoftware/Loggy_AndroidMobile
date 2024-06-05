package com.loggy.jetpackcompose.domains.inventory.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.inventorymodule.components.ProductViewModel
import com.loggy.jetpackcompose.domains.inventory.models.Product
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InventoryEditItem(viewModel: ProductViewModel, navController: NavController, productId: Int){
    // Carga el producto que se va a editar cuando se abre la vista
    LaunchedEffect(key1 = productId) {
        viewModel.getProduct(productId)
    }

    Scaffold {
        EditBodyContent(viewModel, navController)
    }
}

@Composable
fun EditBodyContent(viewModel: ProductViewModel, navController: NavController){
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Editar nombre del Producto",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        TextField(value = state.name ,
            onValueChange = {
                viewModel.onNameChange(it, state.brand, state.stock)
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Editar marca del Producto",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        TextField(value = state.brand , onValueChange = {
            viewModel.onNameChange(state.name, it, state.stock)
        },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Editar cantidad del Producto",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
        TextField(value = state.stock.toString() , onValueChange = {
            val stock = it.toIntOrNull() ?: 0
            viewModel.onNameChange(state.name, state.brand, stock)
        },
            singleLine = true
        )
        Button(onClick = {
            viewModel.updateProduct()
            viewModel.resetState()
            viewModel.showSnackbar = true

        }) {
            Text(text = "Guardar")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Salir")
        }
    }
    if (viewModel.showSnackbar) {
        Snackbar(
            action = {
                Button(onClick = { viewModel.showSnackbar = false }) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Producto actualizado")
        }
    }
}
