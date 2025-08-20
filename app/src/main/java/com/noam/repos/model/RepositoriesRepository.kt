package com.noam.repos.model

import com.noam.repos.model.domain.GitRepository
import com.noam.repos.network.ApiOperation

interface RepositoriesRepository{
    suspend fun fetchRepositories(timeframe: TimeFrame = TimeFrame.LastDay): ApiOperation<List<GitRepository>>
    suspend fun fetchNextPageOfRepositories(): ApiOperation<List<GitRepository>>
    fun clickedRepository(gitRepository: GitRepository)
    fun getClickedRepository() : GitRepository
    suspend fun toggleFavorite(repo: GitRepository)
    suspend fun combineReposWithFavorites(currentListOfRepos: List<GitRepository>): List<GitRepository>
}