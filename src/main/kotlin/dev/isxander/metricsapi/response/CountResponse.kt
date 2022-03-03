package dev.isxander.metricsapi.response

import kotlinx.serialization.Serializable

@Serializable
data class CountResponse(val count: Long, val success: Boolean = true, val error: String? = null)