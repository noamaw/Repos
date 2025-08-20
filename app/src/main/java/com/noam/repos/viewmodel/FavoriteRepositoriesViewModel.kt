package com.noam.repos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noam.repos.model.FavoriteRepositoriesRepository
import com.noam.repos.model.domain.GitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteRepositoriesViewModel(private val repository: FavoriteRepositoriesRepository): ViewModel() {

    private val _favoriteRepositories = MutableStateFlow<List<GitRepository>>(emptyList())
    val favoriteRepositories: StateFlow<List<GitRepository>> get() = _favoriteRepositories

    fun addToFavorites(gitRepository: GitRepository) {
        viewModelScope.launch {
            val success = repository.addToFavorites(gitRepository)
            if (success) {
                // Optionally, you can fetch the updated list of favorite repositories
                println("Added to favorites: $gitRepository")
                fetchFavoriteRepositories()
            } else {
                // Handle the case where adding to favorites failed
            }
        }
    }

    fun removeFromFavorites(gitRepository: GitRepository) {
        viewModelScope.launch {
            repository.removeFromFavorites(gitRepository)
            delay(2000)
            fetchFavoriteRepositories()
        }
    }

    fun fetchFavoriteRepositories() {
      viewModelScope.launch {
          val favorites = repository.getFavoriteRepositories()
          _favoriteRepositories.value = favorites
            println("Fetched favorite repositories: $favorites")
        }
    }
}