package dev.isxander.xanderapi.exception

import io.ktor.server.plugins.*

class UnknownMetricException(val metric: String) : BadRequestException("Unknown metric: $metric")