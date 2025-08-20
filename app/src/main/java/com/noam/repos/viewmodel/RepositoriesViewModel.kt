package com.noam.repos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noam.repos.model.RepositoriesRepository
import com.noam.repos.model.TimeFrame
import com.noam.repos.model.domain.RemoteRepository
import com.noam.repos.ui.screens.RepositoriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepositoriesViewModel(private val repository: RepositoriesRepository): ViewModel() {

    private val _repositories = MutableStateFlow<List<RemoteRepository>>(emptyList())

    private val _uiState = MutableStateFlow<RepositoriesUiState>(RepositoriesUiState.Loading)
    val uiState: StateFlow<RepositoriesUiState> get() = _uiState

    fun fetchRepositories(timeframe: TimeFrame = TimeFrame.LastDay) {
        viewModelScope.launch {
            repository.fetchRepositories(timeframe).onSuccess { fetchedRepositories ->
                _repositories.value = fetchedRepositories
                _uiState.value = RepositoriesUiState.Success(_repositories.value)
            }.onFailure {
            }
        }
    }

    fun fetchNextPage() {
        viewModelScope.launch {
            repository.fetchRepositories().onSuccess { fetchedRepositories ->
                _repositories.value += fetchedRepositories
                _uiState.value = RepositoriesUiState.Success(_repositories.value)
            }.onFailure {
            }
        }
    }
}