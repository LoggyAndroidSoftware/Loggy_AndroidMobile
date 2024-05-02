package com.loggy.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.loggy.jetpackcompose.database.LoggyDB
import com.loggy.jetpackcompose.domains.login.models.repositories.UserRepository
import com.loggy.jetpackcompose.domains.login.views.states.LoginViewModel
import com.loggy.jetpackcompose.navigation.AppNavigation
import com.loggy.jetpackcompose.ui.theme.LoggyJPCTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(this, LoggyDB::class.java, "product-db").fallbackToDestructiveMigration().build()
        val dao = db.dao
        val loginRepository = UserRepository(dao)
        val loginViewModel = LoginViewModel(loginRepository)
        setContent {
            LoggyJPCTheme {
                AppNavigation(loginViewModel)
            }
        }
    }
}

