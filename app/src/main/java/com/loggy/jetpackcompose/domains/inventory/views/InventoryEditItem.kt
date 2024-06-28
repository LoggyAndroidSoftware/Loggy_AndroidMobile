package com.loggy.jetpackcompose.domains.inventory.views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.inventorymodule.components.ProductViewModel
import com.loggy.jetpackcompose.domains.inventory.models.Product
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LoggyBackground2
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InventoryEditItem(viewModel: ProductViewModel, navController: NavController, productId: Int){
    // Carga el producto que se va a editar cuando se abre la vista
    LaunchedEffect(key1 = productId) {
        viewModel.getProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                //Modificar el espacio de los objetos de la barra de navegación
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SkyNightBlue,
                    titleContentColor = LoggyYellow
                ),
                title = {
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.vector_leave),
                        contentDescription = "Inventory Icon",
                        tint = LoggyYellow,
                        modifier = Modifier.aspectRatio(0.05f)
                            .clickable(
                                onClick = {
                                    navController.navigate(AppScreens.InventoryScreen.route)
                                }
                            )

                    )
                }
            )

        },
        containerColor = LoggyBackground2,
    ) {innerPadding ->
        Modifier.padding(innerPadding)
        EditBodyContent(viewModel, navController)
    }
}

@Composable
fun EditBodyContent(viewModel: ProductViewModel, navController: NavController){
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Agregar nombre del producto",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.zillaslab)),
            color = SkyNightBlue
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(value = state.name ,
            onValueChange = {
                viewModel.onNameChange(it, state.brand, state.stock, state.batch)

            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Agregar marca del Producto",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.zillaslab)),
            color = SkyNightBlue//
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(value = state.brand , onValueChange = {
            viewModel.onNameChange(state.name, it, state.stock, state.batch)
        },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Agregar cantidad del Producto",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.zillaslab)),
            color = SkyNightBlue
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(value = state.stock.toString() , onValueChange = {
            val stock = it.toIntOrNull() ?: 0
            viewModel.onNameChange(state.name, state.brand, stock, state.batch)
        },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Agregar Lote del Producto",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.zillaslab)),
            color = SkyNightBlue//
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(value = state.batch , onValueChange = {
            viewModel.onNameChange(state.name, state.brand, state.stock, it)
        },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = SkyNightBlue,
                contentColor = LoggyYellow
            ),
            modifier = Modifier.height(60.dp),
            onClick = {
            viewModel.updateProduct()
            viewModel.resetState()
            viewModel.showSnackbar = true
                navController.navigate(AppScreens.InventoryScreen.route)
        }) {
            Text(text = "Guardar")
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
