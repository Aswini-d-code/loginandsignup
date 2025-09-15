package com.example.sideapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sideapp.data.remote.ApiService
import com.example.sideapp.data.remote.AuthRepository
import com.example.sideapp.ui.LoginScreen
import com.example.sideapp.ui.SignupScreen
import com.example.sideapp.ui.UserListScreen
import com.example.sideapp.utils.SessionManager
import com.example.sideapp.viewmodel.UserViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val api by lazy { ApiService(FirebaseAuth.getInstance()) }
    private val repo by lazy { AuthRepository(api) }
    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Initialize Firebase before using FirebaseAuth
        FirebaseApp.initializeApp(this)

        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(repo, session) as T
            }
        }

        setContent {
            val vm: UserViewModel = viewModel(factory = factory)
            AppHost(vm)
        }
    }
}

@Composable
private fun AppHost(vm: UserViewModel) {
    val isLoggedIn by vm.isLoggedIn.collectAsState()
    val status by vm.status.collectAsState()

    var showSignup by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        if (showSignup) {
            SignupScreen(
                onSignup = { email, password -> vm.signup(email, password) },
                onBackToLogin = { showSignup = false },
                status = status
            )
        } else {
            LoginScreen(
                onLogin = { email, password -> vm.login(email, password) },
                onSignupClick = { showSignup = true },
                status = status
            )
        }
    } else {
        UserListScreen(
            status = status,
            onLogout = { vm.logout() }
        )

    }
}
