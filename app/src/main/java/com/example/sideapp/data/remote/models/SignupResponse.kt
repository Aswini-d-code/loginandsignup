package com.example.sideapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val success: Boolean,
    val message: String,
    val userId: String? = null
)
