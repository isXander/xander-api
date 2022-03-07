package dev.isxander.xanderapi.response

import kotlinx.serialization.Serializable

@Serializable
data class CountResponse(val success: Boolean, val count: Long, val error: String? = null)