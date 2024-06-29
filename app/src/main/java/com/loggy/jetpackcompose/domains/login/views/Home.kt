package com.loggy.jetpackcompose.domains.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LightWhiteBlue

@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightWhiteBlue)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Funciones",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.zillaslab)),
                fontWeight = FontWeight(800),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            )
        )
        Image(
            painter = painterResource(id = R.drawable.icon_home_inventory), // Replace with your image resource
            contentDescription = "Inventory Image",
            modifier = Modifier
                .size(300.dp)
                .clickable {
                    navController.navigate(AppScreens.InventoryScreen.route)
                }
                .padding(start = 30.dp)
        )
        Text(text = "Inventario", fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.zillaslab)), fontWeight = FontWeight(600))
        Image(
            painter = painterResource(id = R.drawable.icon_home_orders), // Replace with your image resource
            contentDescription = "Order's Image",
            modifier = Modifier
                .size(300.dp)
                .clickable {
                    navController.navigate(AppScreens.InventoryScreen.route)
                }
                .padding(start = 30.dp)
        )
        Text(text = "Ordenes", fontSize = 30.sp, fontFamily = FontFamily(Font(R.font.zillaslab)), fontWeight = FontWeight(600))



    }
}