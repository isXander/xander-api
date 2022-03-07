package dev.isxander.xanderapi.exception

import io.ktor.server.plugins.*

class InvalidApplicationException(application: String) : BadRequestException("Invalid application: $application")