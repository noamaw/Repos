package com.noam.repos.model

import com.noam.repos.model.domain.RemoteRepository
import com.noam.repos.network.ApiOperation
import com.noam.repos.network.KtorClient

class RepositoriesRepositoryImpl(private val ktorClient: KtorClient): RepositoriesRepository {
    override suspend fun fetchRepositories(
        timeframe: TimeFrame
    ): ApiOperation<List<RemoteRepository>> {
        return ktorClient.getRepositories(timeframe).onSuccess {
            println("Fetched repositories: $it")
        }.onFailure {
            println("Error fetching repositories: ${it.message}")
        }
    }

    override suspend fun fetchNextPageOfRepositories(): ApiOperation<List<RemoteRepository>> {
        return ktorClient.getNextPage().onSuccess {
            println("Fetched next page of repositories: $it")
        }.onFailure {
            println("Error fetching next page of repositories: ${it.message}")
        }
    }

    private var clickedRepository: RemoteRepository = RemoteRepository.empty()

    override fun clickedRepository(remoteRepository: RemoteRepository) {
        clickedRepository = remoteRepository
    }

    override fun getClickedRepository(): RemoteRepository = clickedRepository
}