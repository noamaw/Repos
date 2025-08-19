package com.noam.repos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noam.repos.model.RepositoriesRepository
import com.noam.repos.model.TimeFrame
import com.noam.repos.model.domain.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepositoriesViewModel(private val repository: RepositoriesRepository): ViewModel() {

    private val _repositories = MutableStateFlow<List<RemoteRepository>>(emptyList())
    val repositories: StateFlow<List<RemoteRepository>> get() = _repositories

    fun fetchRepositories(timeframe: TimeFrame = TimeFrame.LastDay) {
        viewModelScope.launch {
            repository.fetchRepositories(timeframe).onSuccess { fetchedRepositories ->
                _repositories.value = fetchedRepositories
            }.onFailure {
            }
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            repository.fetchRepositories().onSuccess { fetchedRepositories ->
                _repositories.value += fetchedRepositories
            }.onFailure {
            }
        }
    }
}