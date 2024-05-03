package com.loggy.jetpackcompose.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.loggy.jetpackcompose.utils.interfaces.Placeholder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private val gson: Gson = GsonBuilder().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val placeHolder: Placeholder = retrofit.create(Placeholder::class.java)
}