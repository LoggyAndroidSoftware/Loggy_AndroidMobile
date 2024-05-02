package com.loggy.jetpackcompose.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen: AppScreens("login_screen")
    object HomeScreen: AppScreens("home_screen")
    object GreetingsScreen: AppScreens("greetings_screen")
    object LineProductScreen: AppScreens("line_product_screen")
    object InventoryScreen: AppScreens("inventory_screen")
    object InventoryAddItemScreen: AppScreens("inventory_add_item_screen")

}