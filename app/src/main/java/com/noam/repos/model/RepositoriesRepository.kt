package com.noam.repos.model

import com.noam.repos.model.domain.RemoteRepository
import com.noam.repos.network.ApiOperation

interface RepositoriesRepository{
    suspend fun fetchRepositories(timeframe: TimeFrame = TimeFrame.LastDay): ApiOperation<List<RemoteRepository>>
    suspend fun fetchNextPageOfRepositories(): ApiOperation<List<RemoteRepository>>
}