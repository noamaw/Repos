package com.noam.repos.model.domain

import kotlinx.serialization.Serializable

@Serializable
data class RemoteOwner(
    val login: String,
    val id: Int,
    val avatar_url: String,
) {
    companion object {
        fun empty(): RemoteOwner {
            return RemoteOwner(
                login = "",
                id = 0,
                avatar_url = ""
            )
        }
    }
}
