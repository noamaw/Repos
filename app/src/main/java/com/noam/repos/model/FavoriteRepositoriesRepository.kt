package com.noam.repos.model

import com.noam.repos.model.domain.GitRepository

interface FavoriteRepositoriesRepository {
    suspend fun addToFavorites(gitRepository: GitRepository): Boolean
    suspend fun removeFromFavorites(gitRepository: GitRepository)
    suspend fun getFavoriteRepositories(): List<GitRepository>
}