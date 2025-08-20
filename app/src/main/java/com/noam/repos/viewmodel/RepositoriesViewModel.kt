package com.noam.repos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noam.repos.model.RepositoriesRepository
import com.noam.repos.model.TimeFrame
import com.noam.repos.model.domain.GitRepository
import com.noam.repos.ui.components.DataText
import com.noam.repos.ui.screens.RepoDetailsUiState
import com.noam.repos.ui.screens.RepositoriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepositoriesViewModel(private val repository: RepositoriesRepository): ViewModel() {

    private val _repositories = MutableStateFlow<List<GitRepository>>(emptyList())

    private val _uiState = MutableStateFlow<RepositoriesUiState>(RepositoriesUiState.Loading)
    val uiState: StateFlow<RepositoriesUiState> get() = _uiState

    fun fetchRepositories(timeframe: TimeFrame = TimeFrame.LastDay) {
        if (timeframe == TimeFrame.Unknown && _repositories.value.isNotEmpty()) {
            println("Using cached repositories")
            _uiState.value = RepositoriesUiState.Success(_repositories.value)
            return
        }
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

    fun onClickedRepository(gitRepository: GitRepository) {
        repository.clickedRepository(gitRepository)
    }

    fun getActiveRepoDetailsUiState(): RepoDetailsUiState {
        val remoteRepository = repository.getClickedRepository()
        val dataTexts = buildList {
            add(DataText("Username", remoteRepository.owner.login))
            add(DataText("Description", remoteRepository.description ?: "No description"))
            add(DataText("Stars", remoteRepository.stargazers_count.toString()))
            remoteRepository.language?.let {
                add(DataText("Language", it))
            }
            add(DataText("Forks", remoteRepository.forks.toString()))
            add(DataText("Created at", remoteRepository.created_at))
            add(DataText("Link", remoteRepository.html_url))
        }
        val repositoryDetailsUiState = RepoDetailsUiState(
            repo = remoteRepository,
            repoDataTexts = dataTexts
        )

        return repositoryDetailsUiState
    }

    fun addToFavorites(repo: GitRepository) {

    }
}