package com.noam.repos.db

import androidx.room.TypeConverter
import com.noam.repos.model.domain.Owner
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromRepoModel(owner: Owner): String = Json.encodeToString(owner)

    @TypeConverter
    fun toRepoModel(ownerString: String): Owner = Json.decodeFromString(ownerString)
}