package com.noam.repos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noam.repos.model.domain.GitRepository

@Database(entities = [GitRepository::class], version = 1)
@TypeConverters(Converters::class)
abstract class GitRepositoryDatabase : RoomDatabase() {
    abstract fun gitRepositoryDao(): GitRepositoryDao
}