package com.loggy.jetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inventorymodule.components.ProductViewModel
import com.example.login.App.GreetingsScreen
import com.example.login.App.LoginScreen
import com.loggy.jetpackcompose.domains.inventory.views.InventoryAddItem
import com.loggy.jetpackcompose.domains.inventory.views.InventoryMain
import com.loggy.jetpackcompose.domains.login.views.HomeScreen
import com.loggy.jetpackcompose.domains.login.views.LineProductScreen
import com.loggy.jetpackcompose.domains.login.views.states.LoginViewModel

@Composable
fun AppNavigation(viewLoginModel: LoginViewModel, viewProductModel: ProductViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(viewLoginModel, navController)
        }
        composable(AppScreens.GreetingsScreen.route){
            GreetingsScreen(navController)
        }
        composable(AppScreens.LineProductScreen.route){
            LineProductScreen(navController)
        }
        composable(AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(AppScreens.InventoryScreen.route){
            InventoryMain(viewProductModel, navController)
        }
        composable(AppScreens.InventoryAddItemScreen.route){
            InventoryAddItem(viewProductModel, navController)
        }
    }
}