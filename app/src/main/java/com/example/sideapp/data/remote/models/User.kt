package com.example.sideapp.data.remote.models

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@OptIn(InternalSerializationApi::class)

// User object returned by ReqRes API
@Serializable
data class User(
    val id: Int,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val avatar: String? = null
)

@OptIn(InternalSerializationApi::class)
// Response for paginated list of users
@Serializable
data class PagedUsersResponse(
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    val data: List<User>
)
@OptIn(InternalSerializationApi::class)
// Request body for creating a new user
@Serializable
data class CreateUserRequest(
    val name: String,
    val job: String
)
@OptIn(InternalSerializationApi::class)
// Response for create user
@Serializable
data class CreateUserResponse(
    val id: String,
    val name: String,
    val job: String,
    val createdAt: String
)
@OptIn(InternalSerializationApi::class)
// Request body for updating user
@Serializable
data class UpdateUserRequest(
    val name: String,
    val job: String
)
@OptIn(InternalSerializationApi::class)
// Response for update user
@Serializable
data class UpdateUserResponse(
    val name: String,
    val job: String,
    val updatedAt: String
)
