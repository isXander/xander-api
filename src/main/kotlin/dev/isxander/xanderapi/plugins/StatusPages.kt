package dev.isxander.xanderapi.plugins

import dev.isxander.xanderapi.exception.AuthorizationException
import dev.isxander.xanderapi.response.GenericSuccessResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, GenericSuccessResponse(false, cause.message))
        }
        exception<AuthorizationException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, GenericSuccessResponse(false, "Invalid login!"))
        }
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, GenericSuccessResponse(false, cause.javaClass.simpleName + (cause.message ?: "Unknown error")))
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(status, GenericSuccessResponse(false, "Endpoint not found."))
        }
        status(HttpStatusCode.Forbidden) { call, status ->
            call.respond(status, GenericSuccessResponse(false, "Forbidden!"))
        }
    }
}