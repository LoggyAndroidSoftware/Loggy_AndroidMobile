@file:OptIn(ExperimentalMaterial3Api::class)

package com.loggy.jetpackcompose.domains.inventory.views



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.loggy.jetpackcompose.navigation.AppScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryMain(viewModel: ProductViewModel, navController: NavHostController){
    val products by viewModel.products.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getProducts()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Inventory",
                        style = MaterialTheme.typography.headlineLarge,

                        fontFamily = FontFamily(Font(R.font.zillaslab)),
                    )
                }
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_home),
                    contentDescription = "Inventory Icon"
                )
            },

        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
             navController.navigate(AppScreens.InventoryAddItemScreen.route)
        },
            modifier = Modifier
                .width(350.dp)
                .height(100.dp)
                .border(width = 2.dp, color = Color.Black, shape = RectangleShape)
                .background(Color.Transparent)
                .padding(vertical = 10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black
            )

        ) {
            Text(text = "+", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(50.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
           items(products.size){ index ->
               Column(
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Center){
                       Column(modifier = Modifier.fillMaxWidth()) {
                           Text(
                               text = "Nombre del producto: " + products[index].name,
                               fontFamily = FontFamily(Font(R.font.zillaslab)),
                               fontSize = 20.sp
                           )
                           Text(
                               text = "Marca: " + products[index].brand,
                               fontFamily = FontFamily(Font(R.font.zillaslab)),
                               fontSize = 20.sp
                           )
                           Text(
                               text = "Cantidad: " + products[index].stock.toString(),
                               fontFamily = FontFamily(Font(R.font.zillaslab)),
                               fontSize = 20.sp
                           )
                           var number = index

                       }

                   }
                   
               }
               Spacer(modifier = Modifier.height(20.dp))
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