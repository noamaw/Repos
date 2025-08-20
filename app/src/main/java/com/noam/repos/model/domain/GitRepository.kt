package com.noam.repos.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class GitRepository(
    @PrimaryKey
    val id: Int,
    val name: String,
    val private: Boolean,
    val owner: Owner,
    val html_url: String,
    val description: String? = null,
    val stargazers_count: Int = 0,
    val language: String? = null,
    val forks: Int,
    val created_at: String,
    val isFavorite: Boolean = false
) {
    companion object {
        fun empty(): GitRepository {
            return GitRepository(
                id = 0,
                name = "",
                private = false,
                owner = Owner.empty(),
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

