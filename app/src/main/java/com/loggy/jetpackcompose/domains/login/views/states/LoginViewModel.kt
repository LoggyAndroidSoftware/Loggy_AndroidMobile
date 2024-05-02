package com.loggy.jetpackcompose.domains.login.views.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.loggy.jetpackcompose.domains.login.models.repositories.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) {
    var state by mutableStateOf(LoginState())
        private set

    fun inputCredentials(username: String, password: String) {
        state = state.copy(username = username, password = password)
    }
    fun resetState() {
        state = LoginState()
    }

}