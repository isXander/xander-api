package dev.isxander.xanderapi.metrics

import dev.isxander.xanderapi.exception.UnknownMetricException
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.configureMetricsRouting() {
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