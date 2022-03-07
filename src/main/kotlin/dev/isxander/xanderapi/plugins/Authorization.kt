package dev.isxander.xanderapi.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dev.isxander.xanderapi.utils.JWT_SECRET
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuthorization() {
    authentication {
        jwt("auth-jwt") {
            realm = "Internal Calls"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JWT_SECRET))
                    .withIssuer("api.isxander.dev")
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}