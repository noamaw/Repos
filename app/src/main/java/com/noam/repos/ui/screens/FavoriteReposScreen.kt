package com.noam.repos.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noam.repos.ui.components.HeaderRowComponent
import com.noam.repos.ui.components.RepositoryRowComponent
import com.noam.repos.viewmodel.FavoriteRepositoriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteReposScreen(
    favoriteRepositoriesViewModel: FavoriteRepositoriesViewModel = koinViewModel(),
    scaffoldPadding: PaddingValues
) {
    val uiState by favoriteRepositoriesViewModel.favoriteRepositories.collectAsState()

    LaunchedEffect(Unit) {
        favoriteRepositoriesViewModel.fetchFavoriteRepositories()
    }

    Scaffold(modifier = Modifier.fillMaxSize().padding(scaffoldPadding)){ contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ){
            LazyColumn(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState()),
                ) {
                stickyHeader(contentType = "header") {
                    HeaderRowComponent(
                        width = 150.dp
                    )
                }
                items(uiState) { it ->
                    RepositoryRowComponent(
                        repository = it,
                        width = 150.dp,
                        onFavClick = { repo ->
                            Log.d("FavoriteReposScreen", "Toggling favorite for: ${repo.name}")
                            favoriteRepositoriesViewModel.removeFromFavorites(repo)
                        }
                    )
                }
            }
        }
    }
}