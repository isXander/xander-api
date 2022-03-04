package dev.isxander.metricsapi.utils

val PORT = env("PORT")?.toIntOrNull() ?: 8080
val MONGO_ADDRESS = env("MONGO_ADDRESS") ?: "mongodb://localhost"