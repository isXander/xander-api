package dev.isxander.metricsapi

import dev.isxander.metricsapi.plugins.*
import dev.isxander.metricsapi.utils.PORT
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
    embeddedServer(Netty, port = PORT, host = "0.0.0.0") {
        configurePlugins()
    }.start(wait = true)
}
