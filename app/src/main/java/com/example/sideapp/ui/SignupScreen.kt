package com.example.sideapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen(
    onSignup: (String, String) -> Unit,
    onBackToLogin: () -> Unit,
    status: String?
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Sign Up", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { onSignup(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create account")
        }

        TextButton(onClick = onBackToLogin) {
            Text("Already have an account? Log in")
        }

        // ✅ Show signup status
        status?.let {
            val color = if (it.contains("successful", ignoreCase = true)) {
                Color(0xFF2E7D32) // green
            } else {
                Color(0xFFD32F2F) // red
            }
            Spacer(Modifier.height(8.dp))
            Text(it, color = color)
        }
    }
}
