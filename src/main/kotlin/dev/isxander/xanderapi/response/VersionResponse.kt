package dev.isxander.xanderapi.response

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(val success: Boolean, val version: String?)