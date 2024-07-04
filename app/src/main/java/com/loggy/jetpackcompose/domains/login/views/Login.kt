package com.example.login.App


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
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

import com.loggy.jetpackcompose.ui.theme.LoggyBackground
import com.loggy.jetpackcompose.ui.theme.LoggyBackground2
import com.loggy.jetpackcompose.ui.theme.LoggyYellow
import com.loggy.jetpackcompose.ui.theme.SkyNightBlue


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
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val state = viewModel.state
    var termsAccepted by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    // Mejora de la interfaz de usuario (Salto de campos) Experimental
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val (currentFocusRequester, nextFocusRequester) = remember { FocusRequester.createRefs() }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Términos y condiciones") },
            text = {
                Text("Loggy es una aplicación de gestión de inventarios de hidrocarburos. Al utilizar esta Aplicación, usted acepta los siguientes términos y condiciones:\n\n" +
                        "1. Confidencialidad: La Aplicación no divulgará su información personal y confidencial de la empresa sin su consentimiento, a menos que sea requerido por ley.\n\n" +
                        "2. Permisos: La Aplicación puede solicitar permisos para gestionar y crear archivos en su dispositivo. Estos permisos solo se utilizarán para las funciones necesarias de la Aplicación y no se utilizarán para recopilar información personal sin su consentimiento.\n\n" +
                        "3. Uso aceptable: Usted se compromete a utilizar la Aplicación de manera que no infrinja los derechos de otros, ni restrinja o inhiba su uso y disfrute de la Aplicación. No debe utilizar la Aplicación de manera ilegal, inmoral o perjudicial.\n\n" +
                        "4. Limitación de responsabilidad: En la medida máxima permitida por la ley, no seremos responsables de ninguna pérdida o daño que pueda surgir en relación con el uso de esta Aplicación.\n\n" +
                        "Al utilizar esta Aplicación, usted acepta estos términos y condiciones. Si no está de acuerdo con estos términos, no debe utilizar esta Aplicación.")
            },
            confirmButton = {
                Button(onClick =
                { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = LoggyYellow, contentColor = SkyNightBlue),
                    shape = MaterialTheme.shapes.medium) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoggyBackground2)
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
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = LoggyYellow, contentColor = SkyNightBlue),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .size(250.dp, 70.dp)
                .padding(10.dp)
        ) {
            Text("Términos y condiciones")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it }
            )
            Text("Acepto los términos y condiciones")
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(

            onClick = {
                if (!termsAccepted) {
                    Toast.makeText(context, "Debes aceptar los términos y condiciones para continuar", Toast.LENGTH_SHORT).show()
                    return@Button
                }
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
                    if (state.username == "diego.sanchez" && state.password == "developer"){
                        state.loginSuccess = true
                    }
                    if(state.loginSuccess){
                        navController.navigate(AppScreens.GreetingsScreen.route)
                    } else {
                        passwordError = "Credenciales incorrectas"
                        usernameError = "Credenciales incorrectas"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = LoggyYellow, contentColor = SkyNightBlue),
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


