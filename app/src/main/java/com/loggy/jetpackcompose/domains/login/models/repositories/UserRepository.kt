package com.loggy.jetpackcompose.domains.login.models.repositories

import com.loggy.jetpackcompose.domains.login.models.dao.UserDAO

class UserRepository(private val userDAO: UserDAO) {
    suspend fun getUserByEmail(email: String) = userDAO.getUserByEmail(email)
}