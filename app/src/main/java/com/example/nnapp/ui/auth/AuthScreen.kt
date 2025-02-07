package com.example.nnapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel(), navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by viewModel.authState.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState(initial = "")

    // Si el usuario está autenticado, navega a Home y limpia el stack de navegación
    LaunchedEffect(authState) {
        if (authState) {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true } // Evita volver atrás con el botón de Android
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.login(email, password) }) {
            Text("Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.register(email, password) }) {
            Text("Registrarse")
        }

        if (errorMessage?.isNotEmpty() == true) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}