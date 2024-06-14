package com.loggy.jetpackcompose.domains.inventory.views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text


import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.inventorymodule.components.ProductViewModel
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LoggyBackground2
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun InventoryAddItem(viewModel: ProductViewModel, navController: NavController){
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
    ){ innerPadding ->
        Modifier.padding(innerPadding)
        BodyContent(viewModel, navController)
    }

}


@Composable
fun BodyContent(viewModel: ProductViewModel, navController: NavController){
    val state = viewModel.state
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
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
            viewModel.onNameChange(it, state.brand, state.stock)

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
            viewModel.onNameChange(state.name, it, state.stock)
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
            viewModel.onNameChange(state.name, state.brand, stock)
        },
                singleLine = true
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = SkyNightBlue,
                contentColor = LoggyYellow
            ),
            modifier = Modifier.height(60.dp),
            onClick = {
            viewModel.insertProduct()
            viewModel.resetState()
                navController.navigate(AppScreens.InventoryScreen.route)
            })
        {
            Text(
                text = "Guardar",
                fontFamily = FontFamily(Font(R.font.zillaslab)),
                )

        }

    }

    /*
    if (viewModel.showSnackbar) {
        Snackbar(
            action = {
                Button(onClick = { viewModel.showSnackbar = false }) {
                    Text("OK")
                }
            },
            modifier = Modifier.padding(16.dp),

        ) {
            Text(text = "Producto guardado")
        }
    }
     */
}