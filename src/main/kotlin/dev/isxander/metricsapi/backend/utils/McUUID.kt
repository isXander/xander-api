package dev.isxander.metricsapi.backend.utils

import io.ktor.client.request.*
import io.ktor.http.*

data class McUUID(val uuid: String) {
    fun validate(): Boolean {
        return uuid.matches(Regex("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
    }

    suspend fun authenticateWithMojang(): Boolean {
        if (!validate()) return false

        val url = "https://sessionserver.mojang.com/session/minecraft/profile/$uuid"
        val response = httpClient.get(url)
        return response.status == HttpStatusCode.OK
    }
}
