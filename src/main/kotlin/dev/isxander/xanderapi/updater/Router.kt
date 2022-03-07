package dev.isxander.xanderapi.updater

import dev.isxander.xanderapi.response.GenericSuccessResponse
import dev.isxander.xanderapi.response.VersionResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureUpdaterRouting() {
    get("/updater/latest/{application}") {
        val application = call.parameters["application"] ?: throw BadRequestException("Application name is missing")
        val loader = call.request.queryParameters["loader"] ?: throw BadRequestException("Loader is missing")
        val minecraft = call.request.queryParameters["minecraft"] ?: throw BadRequestException("Minecraft version is missing")

        val latest = UpdaterManager.getLatestVersion(application, loader, minecraft)
        call.respond(HttpStatusCode.OK, VersionResponse(latest != null, latest))
    }

    get("/updater/new/{application}") {
        val application = call.parameters["application"] ?: throw BadRequestException("Application name is missing")
        val loader = call.request.queryParameters["loader"] ?: throw BadRequestException("Loader is missing")
        val minecraft = call.request.queryParameters["minecraft"] ?: throw BadRequestException("Minecraft version is missing")
        val version = call.request.queryParameters["version"] ?: throw BadRequestException("Version is missing")

        UpdaterManager.putVersion(application, UpdatableEntry(loader, minecraft, version))
        call.respond(HttpStatusCode.OK, GenericSuccessResponse(true))
    }
}