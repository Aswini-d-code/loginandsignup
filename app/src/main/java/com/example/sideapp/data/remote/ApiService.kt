package com.example.sideapp.data.remote

import com.example.sideapp.data.remote.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ApiService(private val client: HttpClient) {

    // 🔹 Login
    suspend fun login(email: String, password: String): String {
        val response: HttpResponse = client.post("https://reqres.in/api/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }
        val loginResponse: LoginResponse = response.body()
        return loginResponse.token
    }

    // 🔹 Signup
    suspend fun signup(email: String, password: String): String {
        val response: HttpResponse = client.post("https://reqres.in/api/register") {
            contentType(ContentType.Application.Json)
            setBody(SignupRequest(email, password))
        }
        val signupResponse: SignupResponse = response.body()
        return signupResponse.token
    }

    // 🔹 Get paginated users
    suspend fun getUsers(page: Int = 1): List<User> {
        val response: HttpResponse = client.get("https://reqres.in/api/users") {
            url {
                parameters.append("page", page.toString())
            }
        }
        val paged: PagedUsersResponse = response.body()
        return paged.data
    }

    // 🔹 Create a new user
    suspend fun createUser(name: String, job: String): CreateUserResponse {
        val response: HttpResponse = client.post("https://reqres.in/api/users") {
            contentType(ContentType.Application.Json)
            setBody(CreateUserRequest(name, job))
        }
        return response.body()
    }

    // 🔹 Update an existing user
    suspend fun updateUser(id: Int, name: String, job: String): UpdateUserResponse {
        val response: HttpResponse = client.put("https://reqres.in/api/users/$id") {
            contentType(ContentType.Application.Json)
            setBody(UpdateUserRequest(name, job))
        }
        return response.body()
    }

    // 🔹 Delete a user
    suspend fun deleteUser(id: Int) {
        client.delete("https://reqres.in/api/users/$id")
    }
}
