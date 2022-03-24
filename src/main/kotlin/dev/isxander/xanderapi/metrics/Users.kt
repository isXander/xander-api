package dev.isxander.xanderapi.metrics

import dev.isxander.xanderapi.exception.InvalidUUIDException
import dev.isxander.xanderapi.metrics.Metric.Companion.getApplicationCollection
import dev.isxander.xanderapi.response.CountResponse
import dev.isxander.xanderapi.response.GenericSuccessResponse
import dev.isxander.xanderapi.utils.McUUID
import dev.isxander.xanderapi.utils.orBadRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import org.litote.kmongo.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.match
import org.litote.kmongo.sample

object Users : Metric {
    override suspend fun handleGet(ctx: PipelineContext<Unit, ApplicationCall>, application: String) = with(ctx) {
        call.respond(HttpStatusCode.OK, CountResponse(true, getUniqueUsersInfo(application)))
    }

    override suspend fun handlePut(ctx: PipelineContext<Unit, ApplicationCall>, application: String) = with(ctx) {
        val uuid = call.request.queryParameters["uuid"].orBadRequest("require uuid parameter")
        addUser(application, McUUID(uuid))
        call.respond(HttpStatusCode.OK, GenericSuccessResponse(true))
    }

    override suspend fun handleInfo(ctx: PipelineContext<Unit, ApplicationCall>, application: String) = with(ctx) {
        val infoType = call.request.queryParameters["info"]

        when (infoType) {
            "unique" -> call.respond(HttpStatusCode.OK, CountResponse(true, getUniqueUsersInfo(application)))
            "login_count" -> {
                val uuid = call.request.queryParameters["uuid"].orBadRequest("require uuid parameter")
                val loginCount = getLoginCountInfo(application, McUUID(uuid)).orBadRequest("uuid not found")
                call.respond(HttpStatusCode.OK, CountResponse(true, loginCount))
            }
        }
    }

    private suspend fun addUser(application: String, uuid: McUUID) {
        if (!uuid.authenticateWithMojang()) throw InvalidUUIDException(uuid.uuid)

        val col = Metric.database.getApplicationCollection<UUIDWithCount>(application, "users")

        val existingUser = col.findOneAndDelete(UUIDWithCount::uuid eq uuid)
        if (existingUser == null) {
            col.insertOne(UUIDWithCount(uuid, 1))
        } else {
            col.insertOne(existingUser.incrementLogin())
        }
    }

    private fun getUniqueUsersInfo(application: String): Long {
        val col = Metric.database.getApplicationCollection<UUIDWithCount>(application, "users")
        return col.countDocuments()
    }

    private fun getLoginCountInfo(application: String, uuid: McUUID): Long? {
        val col = Metric.database.getApplicationCollection<UUIDWithCount>(application, "users")
        val existingUser = col.aggregate<UUIDWithCount>(match(UUIDWithCount::uuid eq uuid), sample(1)).firstOrNull()
        return existingUser?.loginAmount
    }

    data class UUIDWithCount(val uuid: McUUID, val loginAmount: Long) {
        fun incrementLogin() = UUIDWithCount(uuid, loginAmount + 1)
    }
}