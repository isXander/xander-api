package dev.isxander.xanderapi

import dev.isxander.xanderapi.plugins.*
import dev.isxander.xanderapi.utils.PORT
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun Application.configurePlugins() {
    configureAuthorization()
    configureRouting()
    configureSerialization()
    configureStatusPages()
}

fun main() {
    embeddedServer(Netty, port = PORT, host = "0.0.0.0") {
        configurePlugins()
    }.start(wait = true)
}
