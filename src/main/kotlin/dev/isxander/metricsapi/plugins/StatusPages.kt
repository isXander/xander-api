package dev.isxander.metricsapi.plugins

import dev.isxander.metricsapi.exception.InvalidUUIDException
import dev.isxander.metricsapi.exception.UnknownMetricException
import dev.isxander.metricsapi.response.GenericSuccessResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<UnknownMetricException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, GenericSuccessResponse(false, "Unknown metric: ${cause.metricName}"))
        }
        exception<InvalidUUIDException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, GenericSuccessResponse(false, "Invalid UUID: ${cause.invalidUUID}"))
        }
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, GenericSuccessResponse(false, cause.javaClass.simpleName + (cause.message ?: "Unknown error")))
        }
    }
}