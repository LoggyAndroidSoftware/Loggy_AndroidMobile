package com.loggy.jetpackcompose.domains.login.models

import retrofit2.Response

data class User(
    var id: Int? = null,
    var name: String = "",
    var lastName: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var dni: String = ""
) {
    fun Response<User>.toUser(): User? {
        return this.body()?.let {
            User(
                id = it.id,
                name = it.name,
                lastName = it.lastName,
                username = it.username,
                email = it.email,
                password = it.password,
                dni = it.dni
            )
        }
    }
}
