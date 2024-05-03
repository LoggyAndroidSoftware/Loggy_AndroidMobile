package com.loggy.jetpackcompose.domains.inventory.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text


import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.inventorymodule.components.ProductViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InventoryAddItem(viewModel: ProductViewModel, navController: NavController){


    Scaffold {
        BodyContent(viewModel, navController)
    }


}

@Composable
fun BodyContent(viewModel: ProductViewModel, navController: NavController){
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = state.name , onValueChange = {
            viewModel.onNameChange(it, state.brand, state.stock)
        })
        TextField(value = state.brand , onValueChange = {
            viewModel.onNameChange(state.name, it, state.stock)
        })
        TextField(value = state.stock.toString() , onValueChange = {
            val stock = it.toIntOrNull() ?: 0
            viewModel.onNameChange(state.name, state.brand, stock)
        })
        Button(onClick = {
            viewModel.insertProduct()
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
            Text(text = "Producto guardado")
        }
    }
}