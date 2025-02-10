package com.example.nnapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido a Home", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LogoutButton(navController)
        Spacer(modifier = Modifier.height(16.dp))
        UnconfirmedBudgetsButton(navController)
    }
}

@Composable
fun LogoutButton(navController: NavController) {
    Button(onClick = {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("auth") {
            popUpTo("home") { inclusive = true }
        }
    }) {
        Text("Cerrar Sesión")
    }
}

@Composable
fun UnconfirmedBudgetsButton(navController: NavController) {
    Button(onClick = { navController.navigate("unconfirmedBudgets") }) {
        Text("Ver Presupuestos sin Confirmar")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}