package com.example.sideapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sideapp.data.remote.AuthRepository
import com.example.sideapp.data.remote.models.User
import com.example.sideapp.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repo: AuthRepository,
    private val session: SessionManager
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status

    // 🔹 Login
    fun login(email: String, password: String) {
        viewModelScope.launch {
            repo.login(email, password)
                .onSuccess { uid ->
                    session.saveToken(uid) // store Firebase UID
                    _isLoggedIn.value = true
                    _status.value = "Login successful"
                }
                .onFailure {
                    _status.value = "Login failed: ${it.message}"
                }
        }
    }

    // 🔹 Signup
    fun signup(email: String, password: String) {
        viewModelScope.launch {
            repo.signup(email, password)
                .onSuccess { uid ->
                    session.saveToken(uid)
                    _isLoggedIn.value = true
                    _status.value = "Signup successful"
                }
                .onFailure {
                    _status.value = "Signup failed: ${it.message}"
                }
        }
    }

    // 🔹 Logout
    fun logout() {
        repo.logout()
        session.clearToken()
        _isLoggedIn.value = false
        _status.value = "Logged out"
    }

    // 🔹 Check if user already logged in
    fun checkUserLoggedIn() {
        val uid = repo.currentUserId()
        _isLoggedIn.value = uid != null
        _status.value = if (uid != null) "User already logged in" else "No user session"
    }
}
