package com.noam.repos.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.noam.repos.model.domain.GitRepository

@Dao
interface GitRepositoryDao {
    @Upsert
    suspend fun insert(gitRepository: GitRepository): Long

    @Delete
    suspend fun delete(gitRepository: GitRepository)

    @Query("SELECT * FROM gitRepository")
    suspend fun getAllRepositories(): List<GitRepository>
}