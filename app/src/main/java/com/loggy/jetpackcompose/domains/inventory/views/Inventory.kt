@file:OptIn(ExperimentalMaterial3Api::class)

package com.loggy.jetpackcompose.domains.inventory.views




import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



import com.example.inventorymodule.components.ProductViewModel
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.domains.inventory.views.utils.createPDF

import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LoggyBackground2
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue
import com.loggy.jetpackcompose.utils.permissions.checkPermission
import com.loggy.jetpackcompose.utils.permissions.requestPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeIcon(navController: NavHostController) {
    // Crear un estado mutable para el color del icono
    val iconColor = remember { mutableStateOf(LoggyYellow) }

    Icon(
        painter = painterResource(id = R.drawable.vector_home),
        contentDescription = "Inventory Icon",
        tint = iconColor.value, // Usar el estado del color del icono
        modifier = Modifier
            .aspectRatio(0.05f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        iconColor.value = Color.White
                        tryAwaitRelease()
                        iconColor.value = LoggyYellow
                    }
                )
            }
            .combinedClickable(
                onClick = { navController.navigate(AppScreens.HomeScreen.route) }
            )
    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InventoryMain(viewModel: ProductViewModel, navController: NavHostController){
    val products by viewModel.filteredProducts.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        if (checkPermission(context)) {
            Toast.makeText(context, "Permiso Aceptado", Toast.LENGTH_LONG).show()
        } else {
            requestPermissions(context)
        }
        viewModel.getProducts()

    }
    var showDialog by remember { mutableStateOf(false) }
    var optionDialog = 0
    var productIndexToDelete by remember { mutableIntStateOf(-1) }

    Scaffold(
        containerColor = LoggyBackground2,
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val areOtherFiltersUsed = viewModel.searchFilters.drop(1).any { it } // Ignora el primer filtro (nombre)

                        HomeIcon(navController = navController)
                        /*
                        Icon(
                            painter = painterResource(id = R.drawable.vector_home),
                            contentDescription = "Inventory Icon",
                            tint = iconColor.value,
                            modifier = Modifier
                                .aspectRatio(0.05f)
                                .indication(interactionState, indication = null)
                                .clickable(
                                    onClick = {navController.navigate(AppScreens.HomeScreen.route)},
                                    )
                        );
                        */
                        Text(
                            text = "Inventory",
                            color = LoggyYellow,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily(Font(R.font.zillaslab)),
                            )

                        Icon(
                            painter = painterResource(id = R.drawable.vector_manage_search),
                            contentDescription = "Inventory Icon",
                            tint = if (areOtherFiltersUsed) Color.White else LoggyYellow, // Si se están utilizando otros filtros, el color del icono es blanco
                            modifier = Modifier
                                .aspectRatio(0.05f)
                                .clickable { showDialog = true; optionDialog = 1 }
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.vector_printer),
                            contentDescription = "Inventory Icon",
                            tint = LoggyYellow,
                            modifier = Modifier
                                .aspectRatio(0.05f)
                                .clickable { showDialog = true; optionDialog = 2 }
                        )
                    }

                },

                )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.size(60.dp),
                containerColor = SkyNightBlue,
                contentColor = LoggyYellow,
                onClick = {
                    navController.navigate(AppScreens.InventoryAddItemScreen.route)
                },
            ){
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->

        // Cuadro emergente de filtros
        if (showDialog && optionDialog == 1) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Filtros") },
                text = {
                    val filtros = listOf("Buscar por nombre", "Buscar por marca", "Buscar por stock")

                    LazyColumn {
                        itemsIndexed(filtros) { index, filtro ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = viewModel.searchFilters[index],
                                    onCheckedChange = { isChecked ->
                                        viewModel.searchFilters = viewModel.searchFilters.toMutableList().also { it[index] = isChecked }
                                        viewModel.updateFilters(
                                            searchText = viewModel.searchText,
                                            searchByName = viewModel.searchFilters[0],
                                            searchByBrand = viewModel.searchFilters[1],
                                            searchByStock = viewModel.searchFilters[2]
                                        )
                                    }
                                )
                                Text(filtro, Modifier.clickable {
                                    viewModel.searchFilters = viewModel.searchFilters.toMutableList().also { it[index] = !it[index] }
                                    viewModel.updateFilters(
                                        searchText = viewModel.searchText,
                                        searchByName = viewModel.searchFilters[0],
                                        searchByBrand = viewModel.searchFilters[1],
                                        searchByStock = viewModel.searchFilters[2]
                                    )
                                })
                            }
                        }
                    }
                },
                confirmButton = {
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
        // Cuadro emergente de impresión
        if(showDialog  && optionDialog == 2){
            var fileName by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Imprimir") },
                text = {
                    Column {
                        Text("¿Estás seguro de que quieres imprimir los productos seleccionados?")
                        TextField(
                            value = fileName,
                            onValueChange = { fileName = it },
                            label = { Text("Nombre del archivo") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (fileName.isNotBlank() && isValidFileName(fileName)) {
                            createPDF(products, fileName)
                            showDialog = false
                            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/LoggyInventories"
                            val file = File(path, "$fileName.pdf")
                            if (file.exists()) {
                                Toast.makeText(context, "El archivo PDF se ha guardado con éxito", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Error al guardar el archivo PDF", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "Por favor, ingresa un nombre válido para el archivo", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (showDialog && optionDialog == 3) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmación") },
                text = { Text("¿Estás seguro de que quieres eliminar este elemento?") },
                confirmButton = {
                    Button(onClick = {
                        if (productIndexToDelete != -1) {
                            viewModel.deleteProduct(productIndexToDelete)
                            productIndexToDelete = -1
                        }
                        showDialog = false
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(10.dp))
            SearchWithFilters(viewModel)
            Spacer(modifier = Modifier.height(10.dp))
            if (products.isEmpty()) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Parece que no tienes\nningun elemento en\nesta lista, para agregar un \nproducto, presiona el botón +\n",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.zillaslab)),
                    fontSize = 20.sp
                )
            }
            else {
                LazyColumn {
                    items(products.size) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = products[index].codename, fontWeight = FontWeight.Bold)
                                    Text(text = "Marca: " +products[index].brand + " | " + products[index].stock.toString()+ " Unidades")
                                    // Parsear y formatear la fecha
                                    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                    val targetFormat = SimpleDateFormat("dd 'de' MMMM", Locale("es", "ES"))
                                    val date = originalFormat.parse(products[index].date)
                                    val formattedDate = if (date != null) targetFormat.format(date) else "Fecha no disponible"

                                    // Mostrar la fecha formateada
                                    Text(text = formattedDate)
                                }
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vector_edit),
                                        contentDescription = "Editar producto",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clickable
                                            {
                                                if (products[index].id != null) {
                                                    navController.navigate(
                                                        AppScreens.InventoryEditItemScreen.routeWithId(
                                                            products[index].id!!
                                                        )
                                                    )
                                                } else {
                                                    // Mostrar un mensaje de error o manejar de otra manera
                                                }
                                            }
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_delete),
                                        contentDescription = "Eliminar producto",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clickable {
                                                productIndexToDelete = index
                                                showDialog = true
                                                optionDialog = 3
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}

fun isValidFileName(fileName: String): Boolean {
    return fileName.all { it.isLetterOrDigit() || it == '-' || it == '_' || it == ' ' }
}
@Composable
fun SearchWithFilters(viewModel: ProductViewModel) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                viewModel.updateFilters(searchText = newText,
                    searchByName = viewModel.searchFilters[0],
                    searchByBrand = viewModel.searchFilters[1],
                    searchByStock = viewModel.searchFilters[2])
            },
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
        )
    }
}


