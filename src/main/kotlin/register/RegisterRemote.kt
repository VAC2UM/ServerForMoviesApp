package com.example.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRecieveRemote(
    val login: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)