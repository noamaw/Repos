package com.noam.repos.model

import com.noam.repos.db.GitRepositoryDatabase
import com.noam.repos.model.domain.GitRepository

class FavoriteRepositoriesRepositoryImpl(private val gitRepositoryDatabase: GitRepositoryDatabase) : FavoriteRepositoriesRepository {
    override suspend fun addToFavorites(gitRepository: GitRepository): Boolean {
        gitRepository.copy(isFavorite = true)
        return gitRepositoryDatabase.gitRepositoryDao().insert(gitRepository) > 0
    }

    override suspend fun removeFromFavorites(gitRepository: GitRepository){
        gitRepository.copy(isFavorite = false)
        return gitRepositoryDatabase.gitRepositoryDao().delete(gitRepository)
    }

    override suspend fun getFavoriteRepositories(): List<GitRepository> {
        return gitRepositoryDatabase.gitRepositoryDao().getAllRepositories()
    }
}