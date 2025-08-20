package com.noam.repos.model.domain

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
) {
    companion object {
        fun empty(): RemoteRepository {
            return RemoteRepository(
                id = 0,
                name = "",
                private = false,
                owner = RemoteOwner.empty(),
                html_url = "",
                description = null,
                stargazers_count = 0,
                language = null,
                forks = 0,
                created_at = ""
            )
        }
    }
}

