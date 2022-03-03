package dev.isxander.metricsapi

import dev.isxander.metricsapi.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun Application.configurePlugins() {
    configureRateLimit()
    configureRouting()
    configureSerialization()
    configureStatusPages()
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configurePlugins()
    }.start(wait = true)
}
