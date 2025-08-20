package com.noam.repos.model

import com.noam.repos.model.domain.GitRepository
import com.noam.repos.network.ApiOperation
import com.noam.repos.network.KtorClient

class RepositoriesRepositoryImpl(private val ktorClient: KtorClient): RepositoriesRepository {
    override suspend fun fetchRepositories(
        timeframe: TimeFrame
    ): ApiOperation<List<GitRepository>> {
        return ktorClient.getRepositories(timeframe).onSuccess {
            println("Fetched repositories: $it")
        }.onFailure {
            println("Error fetching repositories: ${it.message}")
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
}