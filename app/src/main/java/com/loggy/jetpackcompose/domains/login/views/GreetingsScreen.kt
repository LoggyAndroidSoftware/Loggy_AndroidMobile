package com.example.login.App

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.LightWhiteBlue
import kotlinx.coroutines.delay

@Composable
fun GreetingsScreen(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightWhiteBlue)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        Image(
            painter = painterResource(id = R.drawable.logo_sin_letras),
            contentDescription = "Logo image",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Text(text = "Bienvenido, Diego Sánchez", fontSize = 28.sp, fontWeight = FontWeight.Medium)

        LaunchedEffect(key1 = true) {
            delay(3000) // Espera 3 segundos
            navController.navigate(AppScreens.LineProductScreen.route) // Navega a la vista deseada
        }

    }
    
}

