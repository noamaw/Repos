package com.noam.repos.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.noam.repos.model.TimeFrame
import com.noam.repos.model.domain.GitRepository
import com.noam.repos.ui.components.HeaderRowComponent
import com.noam.repos.ui.components.LoadingState
import com.noam.repos.ui.components.RepositoryRowComponent
import com.noam.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepositoriesScreen(
    navController: NavHostController,
    timeFrame: TimeFrame,
    repositoriesViewModel: RepositoriesViewModel = koinViewModel(),
    scaffoldPadding: PaddingValues
) {
    val uiState by repositoriesViewModel.uiState.collectAsState()

    val lazyListState = rememberLazyListState()

    LaunchedEffect(timeFrame) {
        repositoriesViewModel.fetchRepositories(timeframe = timeFrame)
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect {
                val lastVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                val totalItemsCount = lazyListState.layoutInfo.totalItemsCount

                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 5) {
                    Log.d("LazyColumnScroll", "Reached near bottom!")
                    repositoriesViewModel.fetchNextPage()
                }
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(scaffoldPadding)
    ) { contentPadding ->
        when (val state = uiState) {
            RepositoriesUiState.Error -> {
                // todo
            }

            RepositoriesUiState.Loading -> LoadingState()
            is RepositoriesUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(contentPadding)
                ){
                    LazyColumn(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()),
                        state = lazyListState
                    ) {
                        stickyHeader(contentType = "header") {
                            HeaderRowComponent(
                                width = 150.dp
                            )
                        }
                        items(state.data.size) { it ->
                            RepositoryRowComponent(
                                repository = state.data.elementAt(it),
                                width = 150.dp,
                                onRowClick = {
                                    repositoriesViewModel.onClickedRepository(it)
                                    navController.navigate(
                                        Screens.RepoDetailsScreen.route
                                    )
                                },
                                onFavClick = {
                                    repositoriesViewModel.onClickedRepository(it)
                                    navController.navigate(
                                        Screens.RepoDetailsScreen.route
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed interface RepositoriesUiState {
    object Error : RepositoriesUiState
    object Loading : RepositoriesUiState
    data class Success(val data: List<GitRepository>) : RepositoriesUiState
}