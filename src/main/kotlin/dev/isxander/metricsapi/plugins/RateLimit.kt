package dev.isxander.metricsapi.plugins

import guru.zoroark.ratelimit.RateLimit
import io.ktor.server.application.*
import java.time.Duration

fun Application.configureRateLimit() {
    install(RateLimit) {
        limit = 5
        timeBeforeReset = Duration.ofMinutes(1)
    }
}