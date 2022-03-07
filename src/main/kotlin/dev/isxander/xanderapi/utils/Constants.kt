package dev.isxander.xanderapi.utils

val PORT = env("PORT")?.toIntOrNull() ?: 8080
val MONGO_ADDRESS = env("MONGO_ADDRESS") ?: "mongodb://localhost"
val JWT_SECRET = env("JWT_SECRET") ?: error("JWT_SECRET not set")