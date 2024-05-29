package com.example.login.App


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController

import com.loggy.jetpackcompose.R
import com.loggy.jetpackcompose.domains.login.views.states.LoginViewModel
import com.loggy.jetpackcompose.navigation.AppScreens
import com.loggy.jetpackcompose.ui.theme.BlueNight
import com.loggy.jetpackcompose.ui.theme.LightWhiteBlue
import com.loggy.jetpackcompose.ui.theme.OrangeYellow
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun LoginScreen( viewLoginModel: LoginViewModel ,navController: NavHostController){
    Scaffold{
        WelcomeScreen(viewLoginModel, navController)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WelcomeScreen(viewModel: LoginViewModel, navController: NavHostController){


    val state = viewModel.state

    var usernameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    // Mejora de la interfaz de usuario (Salto de campos) Experimental
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (currentFocusRequester, nextFocusRequester) = remember { FocusRequester.createRefs() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightWhiteBlue)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_sin_letras),
            contentDescription = "Logo image",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.size(20.dp))

        Text(text = "Loggy", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.size(60.dp))

        Text(text = "Nombre de usuario", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.inputCredentials(it, state.password) },
            label = {
                Text(text = usernameError.ifEmpty { "" })
            },
            //Mejora de interfaz de usuario (Salto de campos) Experimental
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.focusRequester(currentFocusRequester),
            isError = usernameError.isNotEmpty()
        )
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "Contraseña", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.inputCredentials(state.username, it) },
            label = {
                Text(text = passwordError.ifEmpty { "" })
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            modifier = Modifier.focusRequester(nextFocusRequester),
            isError = passwordError.isNotEmpty()
        )
        Spacer(modifier = Modifier.size(20.dp))

        Button(

            onClick = {
                // Verifica si los campos están vacíos
                if (state.username.isEmpty()) {
                    usernameError = "El nombre de usuario es requerido"
                } else {
                    usernameError = ""
                }

                if (state.password.isEmpty()) {
                    passwordError = "La contraseña es requerida"
                } else {
                    passwordError = ""
                }

                // Si no hay errores, realiza la lógica de inicio de sesión
                if (usernameError.isEmpty() && passwordError.isEmpty()) {
                    if (state.username == "die-san" && state.password == "d3v3l0per"){
                        state.loginSuccess = true
                    }
                    if(state.loginSuccess){
                        navController.navigate(AppScreens.GreetingsScreen.route)
                    } else {
                        navController.navigate(AppScreens.LoginScreen.route)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = OrangeYellow, contentColor = BlueNight),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .size(200.dp, 80.dp)
                .padding(10.dp)
            )
         {
            Text("Iniciar Sesión")
        }

    }
}


