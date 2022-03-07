package dev.isxander.xanderapi.updater

import dev.isxander.xanderapi.exception.InvalidApplicationException
import dev.isxander.xanderapi.utils.getButDontCreateCollection
import dev.isxander.xanderapi.utils.mongoClient
import org.bundleproject.libversion.Version
import org.litote.kmongo.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.match

object UpdaterManager {
    val database = mongoClient.getDatabase("updater")

    fun getLatestVersion(application: String, loader: String, minecraft: String): String? {
        val collection = database.getButDontCreateCollection<UpdatableEntry>(application)
            ?: throw InvalidApplicationException(application)

        return collection.aggregate<UpdatableEntry>(
            match(UpdatableEntry::loader eq loader, UpdatableEntry::minecraft eq minecraft),
        ).sortedWith { o1, o2 -> Version.of(o2.version).compareTo(Version.of(o1.version)) }.firstOrNull()?.version
    }

    fun putVersion(application: String, entry: UpdatableEntry) {
        val collection = database.getButDontCreateCollection<UpdatableEntry>(application)
            ?: throw InvalidApplicationException(application)

        collection.insertOne(entry)
    }
}