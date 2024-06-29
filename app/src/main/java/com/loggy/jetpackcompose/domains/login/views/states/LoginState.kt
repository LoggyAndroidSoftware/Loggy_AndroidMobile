package com.loggy.jetpackcompose.domains.login.views.states

import com.loggy.jetpackcompose.domains.login.models.User

data class LoginState (
    val username: String = "",
    val password: String = "",
    val userLogged: User = User(null, "", "", "", "", "", ""),
    var loginSuccess: Boolean = false
)