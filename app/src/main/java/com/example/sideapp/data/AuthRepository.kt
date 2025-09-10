package com.example.sideapp.data.remote

import com.example.sideapp.data.remote.models.*

class AuthRepository(private val api: ApiService) {

    suspend fun login(email: String, password: String): Result<String> =
        runCatching { api.login(email, password) }

    suspend fun signup(email: String, password: String): Result<String> =
        runCatching { api.signup(email, password) }

    suspend fun getUsers(page: Int = 1): Result<List<User>> =
        runCatching { api.getUsers(page) }

    suspend fun createUser(name: String, job: String): Result<CreateUserResponse> =
        runCatching { api.createUser(name, job) }

    suspend fun updateUser(id: Int, name: String, job: String): Result<UpdateUserResponse> =
        runCatching { api.updateUser(id, name, job) }

    suspend fun deleteUser(id: Int): Result<Unit> =
        runCatching { api.deleteUser(id) }
}
