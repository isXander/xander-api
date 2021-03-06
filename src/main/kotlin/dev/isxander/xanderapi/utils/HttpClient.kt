package dev.isxander.xanderapi.utils

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.*
import io.ktor.serialization.kotlinx.json.*

val httpClient = HttpClient(Apache) {
    install(ContentNegotiation) {
        json()
    }
}