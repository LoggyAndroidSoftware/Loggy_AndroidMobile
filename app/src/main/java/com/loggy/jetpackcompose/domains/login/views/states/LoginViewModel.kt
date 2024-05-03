package com.loggy.jetpackcompose.domains.login.views.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.loggy.jetpackcompose.domains.login.models.User
import com.loggy.jetpackcompose.domains.login.models.repositories.UserRepository

class LoginViewModel(
    private val userRepository: UserRepository
) {
    var state by mutableStateOf(LoginState())
        private set

    var userLogged: User = User(null, "", "", "", "", "", "")

    fun inputCredentials(username: String, password: String) {
        state = state.copy(username = username, password = password)
    }
    fun resetState() {
        state = LoginState()
    }

    suspend fun validateUser(username: String, password: String) {
        val userEntity = userRepository.getUserByEmail(username)
        if (userEntity != null && userEntity.password == state.password) {
            state = state.copy(loginSuccess = true, userLogged = userEntity.toUser())
        }
    }

}