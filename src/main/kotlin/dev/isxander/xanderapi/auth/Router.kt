package dev.isxander.xanderapi.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dev.isxander.xanderapi.exception.AuthorizationException
import dev.isxander.xanderapi.response.AuthorizationResponse
import dev.isxander.xanderapi.utils.JWT_SECRET
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import kotlin.time.Duration.Companion.minutes

fun Route.configureAuthorizationRouting() {
    post("/login") {
        val login = call.receive<Login>()

        if (!LoginManager.isValidLogin(login)) {
            throw AuthorizationException()
        }

        val token = JWT.create().run {
            withIssuer("api.isxander.dev")
            withClaim("username", login.username)
            withExpiresAt(Date(System.currentTimeMillis() + 1.minutes.inWholeMilliseconds))
            sign(Algorithm.HMAC256(JWT_SECRET))
        }

        call.respond(AuthorizationResponse(true, token))
    }
}