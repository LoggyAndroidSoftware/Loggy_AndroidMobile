package com.loggy.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.App.GreetingsScreen
import com.example.login.App.LoginScreen
import com.loggy.jetpackcompose.domains.login.views.states.LoginViewModel

@Composable
fun AppNavigation(viewLoginModel: LoginViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(viewLoginModel, navController)
        }
        composable(AppScreens.GreetingsScreen.route){
            GreetingsScreen(navController)
        }
    }
}