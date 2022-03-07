package dev.isxander.xanderapi.response

import kotlinx.serialization.Serializable

@Serializable
data class GenericSuccessResponse(val success: Boolean, val error: String? = null)
