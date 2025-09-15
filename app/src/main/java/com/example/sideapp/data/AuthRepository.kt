package com.example.sideapp.data.remote

class AuthRepository(private val authService: ApiService) {

    suspend fun login(email: String, password: String): Result<String> =
        runCatching { authService.login(email, password) }

    suspend fun signup(email: String, password: String): Result<String> =
        runCatching { authService.signup(email, password) }

    fun logout() = authService.logout()

    fun currentUserId(): String? = authService.currentUserId()
}
