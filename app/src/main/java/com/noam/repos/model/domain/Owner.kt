package com.noam.repos.model.domain

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val login: String,
    val id: Int,
    val avatar_url: String,
) {
    companion object {
        fun empty(): Owner {
            return Owner(
                login = "",
                id = 0,
                avatar_url = ""
            )
        }
    }
}
