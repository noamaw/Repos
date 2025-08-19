package com.noam.repos.models.domain

import kotlinx.serialization.Serializable

@Serializable
data class RemoteOwner(
    val login: String,
    val id: Int,
    val avatar_url: String,
)
