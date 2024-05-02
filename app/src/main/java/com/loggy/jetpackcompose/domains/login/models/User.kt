package com.loggy.jetpackcompose.domains.login.models

data class User(
    var id: Int? = null,
    var name: String,
    var lastName: String,
    var username: String,
    var email: String,
    var password: String,
    var dni: String
)
