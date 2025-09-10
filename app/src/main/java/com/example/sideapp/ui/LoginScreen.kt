package com.example.sideapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onSignupClick: () -> Unit,
    status: String?
) {
    var email by remember { mutableStateOf("eve.holt@reqres.in") }
    var password by remember { mutableStateOf("cityslicka") }

    Column(Modifier.padding(16.dp)) {
        Text("Login", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(password, { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(16.dp))
        Button(onClick = { onLogin(email, password) }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }

        TextButton(onClick = onSignupClick) { Text("No account? Sign up") }

        status?.let { Text(it, color = MaterialTheme.colorScheme.secondary) }
    }
}
