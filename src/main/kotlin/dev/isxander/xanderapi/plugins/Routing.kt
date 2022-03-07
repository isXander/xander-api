package dev.isxander.xanderapi.plugins

import dev.isxander.xanderapi.auth.configureAuthorizationRouting
import dev.isxander.xanderapi.metrics.configureMetricsRouting
import dev.isxander.xanderapi.updater.configureUpdaterRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        configureMetricsRouting()
        configureAuthorizationRouting()
        configureUpdaterRouting()
    }
}
