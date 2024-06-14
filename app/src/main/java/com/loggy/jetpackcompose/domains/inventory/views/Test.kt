package com.loggy.jetpackcompose.domains.inventory.views


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens

import com.loggy.jetpackcompose.ui.theme.LoggyBackground2
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestView() {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        containerColor = LoggyBackground2,
        topBar = {

            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                //Modificar el espacio de los objetos de la barra de navegación
                colors = topAppBarColors(
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
                        Icon(
                            painter = painterResource(id = R.drawable.vector_home),
                            contentDescription = "Inventory Icon",
                            tint = LoggyYellow,
                            modifier = Modifier.aspectRatio(0.05f)
                        );

                        Text(
                            text = "Inventory",
                            color = LoggyYellow,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily(Font(R.font.zillaslab)),

                        );
                        Icon(
                            painter = painterResource(id = R.drawable.vector_manage_search),
                            contentDescription = "Inventory Icon",
                            tint = LoggyYellow,
                            modifier = Modifier.aspectRatio(0.05f)
                                .clickable{ showDialog = true }

                        );
                        Icon(
                            painter = painterResource(id = R.drawable.vector_printer),
                            contentDescription = "Inventory Icon",
                            tint = LoggyYellow,
                            modifier = Modifier.aspectRatio(0.05f)
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
                    navController.navigate(AppScreens.InventoryAddItemScreen.route
                },
            ){
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
                // Cuadro emergente de filtros
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Filtros") },
                text = {
                    val filtros = listOf("Filtro 1", "Filtro 2", "Filtro 3")
                    LazyColumn {
                        items(filtros) { filtro ->
                            Text(filtro, Modifier.clickable { /* Manejar clic en el filtro */ })
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
        
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(10.dp))
            SearchWithFilters()
            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn {
                items(products.size) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = products[index].name, fontWeight = FontWeight.Bold)
                                Text(text = products[index].brand + " - " + products[index].stock.toString())
                            }
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.End
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.vector_edit),
                                    contentDescription = "Editar producto",
                                    modifier = Modifier
                                        .size(24.dp)
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
                                        .size(24.dp)
                                        .clickable { viewModel.deleteProduct(index) }
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Parece que no tienes\nningun elemento en\nesta lista, para agregar un \nproducto, presiona el botón +\n",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.zillaslab)),
                )
            }

        }
    }

}


@Composable
fun SearchWithFilters() {

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        TextField(
            value = searchText,
            onValueChange = { newText -> searchText = newText },
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {  }
        )
        // ...
    }

}

*/