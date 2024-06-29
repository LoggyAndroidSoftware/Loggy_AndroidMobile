package com.loggy.jetpackcompose.domains.inventory.views

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold


import androidx.compose.material3.Text


import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable



import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


import com.example.inventorymodule.components.ProductViewModel
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LoggyBackground2
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue
import java.util.Locale


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
    val context = LocalContext.current
    val state = viewModel.state
    val validBrands = listOf("Mobil", "Texaco", "Vistony", "Petroperu")
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
        TextField(
            value = state.brand,
            onValueChange = {
                val capitalizedBrand = it.capitalize(Locale.ROOT)
                viewModel.onNameChange(state.name, capitalizedBrand, state.stock, state.batch)
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                if (state.brand.lowercase(Locale.ROOT) !in validBrands) {
                    Toast.makeText(context, "La marca no es válida", Toast.LENGTH_SHORT).show()
                    viewModel.onNameChange(state.name, "", state.stock, state.batch) // Reset the brand field
                }
            })
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
            viewModel.onNameChange(state.name, state.brand, stock,  state.batch)
        },
                singleLine = true
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Agregar lote del Producto",
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
                if (state.name.isBlank() || state.brand.isBlank() || state.batch.isBlank()) {
                    Toast.makeText(context, "Todos los campos deben ser rellenados", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insertProduct()
                    viewModel.resetState()
                    navController.navigate(AppScreens.InventoryScreen.route)
                }
            }
        ) {
            Text(
                text = "Guardar",
                fontFamily = FontFamily(Font(R.font.zillaslab)),
            )
        }

    }

}

/*
@Composable
fun ProductForm(viewModel: ProductViewModel, state: ProductState) {
    val productsAndBrands = listOf(
        Pair("Mobil 1", "Mobil"),
        Pair("Mobil Super", "Mobil"),
        Pair("Mobil Delvac", "Mobil"),
        Pair("Mobil 1 Extended Performance", "Mobil"),
        Pair("Mobil 1 High Mileage", "Mobil"),
        Pair("Texaco Havoline", "Texaco"),
        Pair("Texaco Ursa", "Texaco"),
        Pair("Texaco Delo", "Texaco"),
        Pair("Texaco Premium ATF", "Texaco"),
        Pair("Vistony Max Pro", "Vistony"),
        Pair("Vistony Full Synthetic", "Vistony"),
        Pair("Vistony Diesel Supreme", "Vistony"),
        Pair("Vistony Racing Oil", "Vistony"),
        Pair("Vistony Ultra Protection", "Vistony"),
        Pair("Petro-Perú Súper", "Petro-Perú"),
        Pair("Petro-Perú Multigrado", "Petro-Perú"),
        Pair("Petro-Perú Sintético", "Petro-Perú"),
        Pair("Petro-Perú Premium", "Petro-Perú"),
        Pair("Petro-Perú Diesel", "Petro-Perú"),
        Pair("Mobil 1 Racing", "Mobil"),
        Pair("Mobil 1 Turbo Diesel", "Mobil"),
        Pair("Mobil Super 1000", "Mobil"),
        Pair("Mobil Super 2000", "Mobil"),
        Pair("Texaco Havoline Synthetic Blend", "Texaco"),
        Pair("Texaco Havoline Full Synthetic", "Texaco"),
        Pair("Texaco Delo 400", "Texaco"),
        Pair("Texaco Ursa Super Plus", "Texaco"),
        Pair("Vistony Eco Power", "Vistony"),
        Pair("Vistony High Mileage", "Vistony"),
        Pair("Petro-Perú Max Diesel", "Petro-Perú"),
        Pair("Mobil 1 ESP Formula", "Mobil"),
        Pair("Mobil Super 3000", "Mobil"),
        Pair("Texaco Advanced Formula", "Texaco"),
        Pair("Texaco Premium Diesel", "Texaco"),
        Pair("Vistony Gold Series", "Vistony"),
        Pair("Vistony Super Protection", "Vistony"),
        Pair("Petro-Perú Synthetic Blend", "Petro-Perú"),
        Pair("Petro-Perú Premium Full Synthetic", "Petro-Perú"),
        Pair("Mobil 1 Annual Protection", "Mobil"),
        Pair("Texaco Ursa HD", "Texaco")
    )
    val productSuggestions = productsAndBrands.map { it.first }
    var selectedProduct by remember { mutableStateOf("") }
    var selectedBrand by remember { mutableStateOf("") }

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

        // Aquí es donde se implementa el autocompletado
        AutocompleteBox(
            items = productSuggestions,
            filter = { query, item -> item.contains(query, ignoreCase = true) },
            onSelect = { item ->
                selectedProduct = item
                selectedBrand = productsAndBrands.find { it.first == item }?.second ?: ""
                viewModel.onNameChange(selectedProduct, selectedBrand, state.stock, state.batch)
            }
        ) { suggestions, onSuggestionClick ->
            TextField(
                value = state.name,
                onValueChange = {
                    viewModel.onNameChange(it, state.brand, state.stock, state.batch)
                },
                label = { Text("Nombre del producto") },
                singleLine = true
            )
            DropdownMenu(
                expanded = suggestions.isNotEmpty(),
                onDismissRequest = { /* No hacer nada */ }
            ) {
                suggestions.forEach { suggestion ->

                    DropdownMenuItem(onClick = {
                        onSuggestionClick(suggestion)
                        viewModel.onNameChange(suggestion, state.brand, state.stock, state.batch)
                    }) {
                        Text(text = suggestion)
                    }
                }
            }
        }

        // Resto del código
    }
}
@Composable
fun AutocompleteBox(
    items: List<String>,
    filter: (String, String) -> Boolean,
    onSelect: (String) -> Unit,
    content: @Composable (List<String>, (String) -> Unit) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val suggestions = remember(query) { items.filter { filter(query, it) } }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar") },
            singleLine = true
        )
        content(suggestions, onSelect)
    }
}
*/
