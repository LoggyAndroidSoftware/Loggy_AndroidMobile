package com.loggy.jetpackcompose.domains.login.views.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.loggy.jetpackcompose.domains.login.models.User
import com.loggy.jetpackcompose.domains.login.models.repositories.UserRepository
import com.loggy.jetpackcompose.utils.RetrofitClient
import com.loggy.jetpackcompose.utils.interfaces.Placeholder
import com.loggy.jetpackcompose.utils.interfaces.UserRequest
import retrofit2.Response

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel(){
    var state by mutableStateOf(LoginState())
        private set

    var userLogged: User = User(null, "", "", "", "", "", "")

    fun inputCredentials(username: String, password: String) {
        state = state.copy(username = username, password = password)
    }
    fun resetState() {
        state = LoginState()
    }
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

     suspend fun loginUser(username: String, password: String) {
        val request = UserRequest(username = username, password = password)
        val response = RetrofitClient.placeHolder.loginUser(request)
        if (response.isSuccessful && response.body() != null) {
            state = state.copy(loginSuccess = true, userLogged = response.toUser()!!)
        } else {
            state = state.copy(loginSuccess = false)
            // Aquí puedes manejar el caso en que la autenticación falla
        }
    }
    suspend fun validateUser(username: String, password: String) {
        val userEntity = userRepository.getUserByEmail(username)
        if (userEntity != null && userEntity.password == state.password) {
            state = state.copy(loginSuccess = true, userLogged = userEntity.toUser())
        }
    }

}