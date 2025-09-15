package com.example.sideapp.data.remote.models

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable


@OptIn(InternalSerializationApi::class)

@Serializable
data class SignupResponse(
    val id: Int? = null,
    val token: String? = null,
    val error: String? = null
)