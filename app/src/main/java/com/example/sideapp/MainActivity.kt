package com.example.sideapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sideapp.data.remote.ApiService
import com.example.sideapp.data.remote.AuthRepository
import com.example.sideapp.ui.LoginScreen
import com.example.sideapp.ui.SignupScreen
import com.example.sideapp.ui.UserListScreen
import com.example.sideapp.utils.SessionManager
import com.example.sideapp.viewmodel.UserViewModel
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val http by lazy { HttpClient(OkHttp) }
    private val api by lazy { ApiService(http) }
    private val repo by lazy { AuthRepository(api) }
    private val session by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(repo, session) as T
            }
        }

        setContent {
            MaterialTheme {
                val vm: UserViewModel = viewModel(factory = factory)
                AppHost(vm)
            }
        }
    }
}

@Composable
private fun AppHost(vm: UserViewModel) {
    val isLoggedIn by vm.isLoggedIn.collectAsState()
    val status by vm.status.collectAsState()
    val users by vm.users.collectAsState()

    var showSignup by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        if (showSignup) {
            SignupScreen(
                onSignup = { e, p -> vm.signup(e, p) },
                onBackToLogin = { showSignup = false },
                status = status
            )
        } else {
            LoginScreen(
                onLogin = { e, p -> vm.login(e, p) },
                onSignupClick = { showSignup = true },
                status = status
            )
        }
    } else {
        UserListScreen(
            users = users,
            status = status,
            onRefresh = { vm.refreshUsers() },
            onCreate = { name, job -> vm.createUser(name, job) },
            onUpdate = { id, name, job -> vm.updateUser(id, name, job) },
            onDelete = { id -> vm.deleteUser(id) },
            onLogout = { vm.logout() }
        )
    }
}
