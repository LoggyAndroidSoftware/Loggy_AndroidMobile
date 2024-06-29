package com.loggy.jetpackcompose.domains.inventory.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Item(navController: NavController) {
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
        content = {
            ItemCard(Modifier.padding(it))
        }
    )
}

@Preview
@Composable
fun ItemCard(modifier: Modifier = Modifier){
    Box(modifier = Modifier.fillMaxSize()
        , contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_lineproduction_oilcan2 ),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                )
                Text(text = "Your Text 1", modifier = Modifier.padding(top = 16.dp))
                Text(text = "Your Text 2", modifier = Modifier.padding(top = 8.dp))
                Text(text = "Your Text 3", modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}