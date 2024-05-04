package com.loggy.jetpackcompose.utils.interfaces


import com.loggy.jetpackcompose.domains.login.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class  UserRequest(
    val username: String = "",
    val password: String = "",

)
interface Placeholder {
    @POST("user/login")
    suspend fun loginUser(@Body request: UserRequest): Response<User>

    /*
    @POST("user")
    suspend fun loginUser(@Body user: User): Response<User>
    */
    @GET("user")
    suspend fun getUsers():Response<List<User>>

}