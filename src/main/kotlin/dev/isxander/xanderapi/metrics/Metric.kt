package dev.isxander.xanderapi.metrics

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import dev.isxander.xanderapi.utils.getButDontCreateCollection
import dev.isxander.xanderapi.utils.mongoClient
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

interface Metric {
    suspend fun handlePut(ctx: PipelineContext<Unit, ApplicationCall>, application: String)
    suspend fun handleInfo(ctx: PipelineContext<Unit, ApplicationCall>, application: String)

    @Deprecated("Use info instead")
    suspend fun handleGet(ctx: PipelineContext<Unit, ApplicationCall>, application: String)

    companion object {
        val database: MongoDatabase = mongoClient.getDatabase("metrics")

        inline fun <reified T : Any> MongoDatabase.getApplicationCollection(application: String, collection: String): MongoCollection<T> =
            getButDontCreateCollection("$application.$collection")!!

        val metricTypes = mapOf<String, Metric>(
            "unique_users" to Users,
            "users" to Users
        )
    }
}