package dev.isxander.metricsapi.backend

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import dev.isxander.metricsapi.utils.MONGO_ADDRESS
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

val mongoClient = KMongo.createClient(MONGO_ADDRESS)
val database = mongoClient.getDatabase("metrics")

inline fun <reified T : Any> MongoDatabase.getApplicationCollection(application: String, collection: String): MongoCollection<T> =
    getCollection<T>("$application.$collection")
