package com.noam.repos.models.domain

import kotlinx.serialization.Serializable

@Serializable
data class RemoteRepository(
    val id: Int,
    val name: String,
    val private: Boolean,
    val owner: RemoteOwner,
    val html_url: String,
    val description: String? = null,
    val stargazers_count: Int = 0,
    val language: String? = null,
    val forks: Int,
    val created_at: String,
)

