package com.example.sideapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val token: String? = null,
    val message: String? = null
)
