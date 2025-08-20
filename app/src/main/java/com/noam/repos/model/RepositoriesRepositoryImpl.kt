package com.noam.repos.model

import com.noam.repos.model.domain.GitRepository
import com.noam.repos.network.ApiOperation
import com.noam.repos.network.KtorClient

class RepositoriesRepositoryImpl(private val ktorClient: KtorClient, private val favoriteRepositoriesRepository: FavoriteRepositoriesRepository): RepositoriesRepository {
    suspend fun getFavoriteRepositories(): List<GitRepository> {
        return favoriteRepositoriesRepository.getFavoriteRepositories()
    }

    override suspend fun fetchRepositories(
        timeframe: TimeFrame
    ): ApiOperation<List<GitRepository>> {
        val result = ktorClient.getRepositories(timeframe).onSuccess {
            println("Fetched repositories: $it")
        }.onFailure {
            println("Error fetching repositories: ${it.message}")
        }
        val favorites = getFavoriteRepositories()
        return result.mapSuccess { repositories ->
            repositories.map { repository ->
                if (favorites.any { it.id == repository.id }) {
                    repository.copy(isFavorite = true)
                } else {
                    repository
                }
            }
        }
    }

    override suspend fun fetchNextPageOfRepositories(): ApiOperation<List<GitRepository>> {
        return ktorClient.getNextPage().onSuccess {
            println("Fetched next page of repositories: $it")
        }.onFailure {
            println("Error fetching next page of repositories: ${it.message}")
        }
    }

    private var clickedRepository: GitRepository = GitRepository.empty()

    override fun clickedRepository(gitRepository: GitRepository) {
        clickedRepository = gitRepository
    }

    override fun getClickedRepository(): GitRepository = clickedRepository

    override suspend fun toggleFavorite(repo: GitRepository) {
        if (repo.isFavorite) {
            favoriteRepositoriesRepository.removeFromFavorites(repo)
        } else {
            favoriteRepositoriesRepository.addToFavorites(repo)
        }
    }

    override suspend fun combineReposWithFavorites(currentListOfRepos: List<GitRepository>): List<GitRepository> {
        val favorites = getFavoriteRepositories()
        return currentListOfRepos.map { repository ->
            if (favorites.any { it.id == repository.id }) {
                repository.copy(isFavorite = true)
            } else {
                repository
            }
        }
    }
}