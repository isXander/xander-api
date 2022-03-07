package dev.isxander.xanderapi.metrics

import dev.isxander.xanderapi.metrics.Metric.Companion.database
import dev.isxander.xanderapi.metrics.Metric.Companion.getApplicationCollection
import dev.isxander.xanderapi.utils.McUUID
import dev.isxander.xanderapi.exception.InvalidUUIDException
import dev.isxander.xanderapi.response.CountResponse
import dev.isxander.xanderapi.response.GenericSuccessResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import org.litote.kmongo.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.match
import org.litote.kmongo.sample

object UniqueUsers : Metric {
    override suspend fun handleGet(ctx: PipelineContext<Unit, ApplicationCall>, application: String) = with(ctx) {
        call.respond(HttpStatusCode.OK, CountResponse(true, getUniqueUsers(application)))
    }

    override suspend fun handlePut(ctx: PipelineContext<Unit, ApplicationCall>, application: String) = with(ctx) {
        val uuid = call.request.queryParameters["uuid"]!!
        addUser(application, McUUID(uuid))
        call.respond(HttpStatusCode.OK, GenericSuccessResponse(true))
    }

    private suspend fun addUser(application: String, uuid: McUUID) {
        if (!uuid.authenticateWithMojang()) throw InvalidUUIDException(uuid.uuid)

        val col = database.getApplicationCollection<McUUID>(application, "unique_users")
        val existingUser = col.aggregate<McUUID>(match(McUUID::uuid eq uuid.uuid), sample(1)).firstOrNull()
        if (existingUser == null) {
            col.insertOne(uuid)
        }
    }

    private fun getUniqueUsers(application: String): Long {
        val col = database.getApplicationCollection<McUUID>(application, "unique_users")
        return col.countDocuments()
    }
}