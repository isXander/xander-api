package dev.isxander.xanderapi.auth

import kotlinx.serialization.Serializable

@Serializable
data class Login(val username: String, val password: String)
