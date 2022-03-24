package dev.isxander.xanderapi.utils

import io.ktor.server.plugins.*

fun <T> T?.orBadRequest(message: String): T = this ?: throw BadRequestException(message)