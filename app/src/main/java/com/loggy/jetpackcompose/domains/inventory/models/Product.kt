package com.loggy.jetpackcompose.domains.inventory.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Product(

    val id: Int? = null,
    val codename: String,
    val brand:String,
    val stock: Int,
    val batch: String,
    val date: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
        Locale.getDefault()).format(Date())

)

