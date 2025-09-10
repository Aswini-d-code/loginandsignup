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

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repo.login(email, password)
                .onSuccess { token ->
                    session.saveToken(token)
                    _isLoggedIn.value = true
                    _status.value = "Login successful"
                }
                .onFailure {
                    _status.value = "Login failed: ${it.message}"
                }
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            repo.signup(email, password)
                .onSuccess { token ->
                    session.saveToken(token)
                    _isLoggedIn.value = true
                    _status.value = "Signup successful"
                }
                .onFailure {
                    _status.value = "Signup failed: ${it.message}"
                }
        }
    }

    fun refreshUsers() {
        viewModelScope.launch {
            repo.getUsers()
                .onSuccess { _users.value = it }
                .onFailure { _status.value = "Failed to load users: ${it.message}" }
        }
    }

    fun createUser(name: String, job: String) {
        viewModelScope.launch {
            repo.createUser(name, job)
                .onSuccess { _status.value = "Created user: ${it.name}" }
                .onFailure { _status.value = "Create failed: ${it.message}" }
        }
    }

    fun updateUser(id: Int, name: String, job: String) {
        viewModelScope.launch {
            repo.updateUser(id, name, job)
                .onSuccess { _status.value = "Updated user: ${it.name}" }
                .onFailure { _status.value = "Update failed: ${it.message}" }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            repo.deleteUser(id)
                .onSuccess { _status.value = "Deleted user $id" }
                .onFailure { _status.value = "Delete failed: ${it.message}" }
        }
    }

    fun logout() {
        viewModelScope.launch {
            session.clearToken()
            _isLoggedIn.value = false
            _status.value = "Logged out"
        }
    }
}
