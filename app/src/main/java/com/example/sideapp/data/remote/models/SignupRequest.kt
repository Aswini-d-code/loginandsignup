package com.example.sideapp.data.remote.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Serializable
data class SignupRequest(
    val email: String,
    val password: String
)
