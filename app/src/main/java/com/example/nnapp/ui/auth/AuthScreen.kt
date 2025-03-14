package com.example.nnapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nnapp.ui.viewmodel.AuthViewModel

/**
 * Pantalla de autenticación con inicio de sesión y registro.
 */
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val authState by viewModel.authState.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState(initial = "")

    // Navega a Home si el usuario está autenticado
    LaunchedEffect(authState) {
        if (authState) {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }

    // Llamamos a la UI pasándole los eventos y datos necesarios
    AuthScreenContent(
        onLogin = { email, password -> viewModel.login(email, password) },
        onRegister = { email, password -> viewModel.register(email, password) },
        errorMessage = errorMessage
    )
}

/**
 * UI de la pantalla de autenticación.
 */
@Composable
fun AuthScreenContent(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String) -> Unit,
    errorMessage: String?
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { if (email.isNotEmpty() && password.isNotEmpty()) onLogin(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { if (email.isNotEmpty() && password.isNotEmpty()) onRegister(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        // Muestra error si hay mensaje
        if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * Preview con un ViewModel falso para poder ver la UI sin errores.
 */
@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    val fakeViewModel = object {
        val fakeError = remember { mutableStateOf("") }
        fun fakeLogin(email: String, password: String) {
            fakeError.value = "Error de prueba"
        }
        fun fakeRegister(email: String, password: String) {
            fakeError.value = "Registro de prueba"
        }
    }

    AuthScreenContent(
        onLogin = { email, password -> fakeViewModel.fakeLogin(email, password) },
        onRegister = { email, password -> fakeViewModel.fakeRegister(email, password) },
        errorMessage = fakeViewModel.fakeError.value
    )
}