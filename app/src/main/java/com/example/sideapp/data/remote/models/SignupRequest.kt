package com.example.sideapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String
)
