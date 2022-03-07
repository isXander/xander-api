package dev.isxander.xanderapi.auth

import dev.isxander.xanderapi.utils.mongoClient
import org.litote.kmongo.*

object LoginManager {
    private val database = mongoClient.getDatabase("authentication")
    private val collection = database.getCollection<Login>("logins")

    fun isValidLogin(login: Login): Boolean {
        return collection.aggregate<Login>(match(Login::username eq login.username), match(Login::password eq login.password)).firstOrNull() != null
    }
}