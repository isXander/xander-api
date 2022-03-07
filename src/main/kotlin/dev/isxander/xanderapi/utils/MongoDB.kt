package dev.isxander.xanderapi.utils

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

val mongoClient = KMongo.createClient()

inline fun <reified T : Any> MongoDatabase.getButDontCreateCollection(name: String): MongoCollection<T>? {
    if (this.listCollectionNames().any { it == name }) {
        return getCollection<T>(name)
    }

    return null
}
