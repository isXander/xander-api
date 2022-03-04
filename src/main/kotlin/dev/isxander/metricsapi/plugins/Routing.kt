package dev.isxander.metricsapi.plugins

import dev.isxander.metricsapi.backend.metric.Metric
import dev.isxander.metricsapi.exception.UnknownMetricException
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/metric/get/{application}") {
            val application = call.parameters["application"]!!
            val metric = call.request.queryParameters["type"]!!

            Metric.metricTypes[metric]?.handleGet(this, application) ?: throw UnknownMetricException(metric)
        }

        get("/metric/put/{application}") {
            val application = call.parameters["application"]!!
            val metric = call.request.queryParameters["type"]!!

            Metric.metricTypes[metric]?.handlePut(this, application) ?: throw UnknownMetricException(metric)
        }
    }
}
