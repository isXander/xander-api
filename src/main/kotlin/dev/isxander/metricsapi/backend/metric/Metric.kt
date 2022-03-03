package dev.isxander.metricsapi.backend.metric

import io.ktor.server.application.*
import io.ktor.util.pipeline.*

interface Metric {
    suspend fun handleGet(ctx: PipelineContext<Unit, ApplicationCall>, application: String)
    suspend fun handlePut(ctx: PipelineContext<Unit, ApplicationCall>, application: String)

    companion object {
        val metricTypes = mapOf<String, Metric>(
            "unique_users" to UniqueUsers
        )
    }
}