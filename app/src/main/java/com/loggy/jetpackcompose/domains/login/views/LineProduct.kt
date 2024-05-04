package com.loggy.jetpackcompose.domains.login.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens


@Composable
fun LineProductScreen(navController: NavController){

    val imageList = listOf(
        R.drawable.icon_lineproduction_oilcan,
        R.drawable.icon_lineproduction_oilcan2,
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Text(
            text = "Selecciona tu linea de producción donde vas a trabajar",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.zillaslab)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),

                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.size(100.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            content = {
                items(6) { index ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                            Image(
                                painter = painterResource(id = imageList[index % imageList.size]), // Replace with your image resource
                                contentDescription = "Image $index",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clickable {
                                        navController.navigate(AppScreens.HomeScreen.route)
                                        }
                            )
                            Text(text = "Línea ${index + 1}")
                        }

                    }

            }
        )
    }
}