package com.noam.repos.model.domain

import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RemoteRepository>
)
