package dev.isxander.xanderapi.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse(val success: Boolean, val token: String)