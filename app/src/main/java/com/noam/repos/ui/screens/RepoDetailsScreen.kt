package com.noam.repos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.noam.repos.model.domain.GitRepository
import com.noam.repos.ui.components.DataText
import com.noam.repos.ui.components.DataTextComponent
import com.noam.repos.ui.components.ImagePlaceHolder
import com.noam.repos.ui.components.LoadingState
import com.noam.repos.ui.components.PrimaryButton
import com.noam.repos.ui.components.SimpleToolbar
import com.noam.repos.viewmodel.FavoriteRepositoriesViewModel
import com.noam.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.compose.koinViewModel

data class RepoDetailsUiState(
    val repo: GitRepository,
    val repoDataTexts: List<DataText>
)

@Composable
fun RepoDetailsScreen(
    viewModel: RepositoriesViewModel = koinViewModel(),
    favoriteRepositoriesViewModel: FavoriteRepositoriesViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {

    val repoDetailsViewState = viewModel.getActiveRepoDetailsUiState()

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(contentPadding))
        {
            SimpleToolbar(title = "Repo details", onBackAction = onBackClicked)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                // Name plate
                item {
                    DataTextComponent(
                        DataText(
                            title = "Repository",
                            text = repoDetailsViewState.repo.name
                        )
                    )
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
                // Image
                item {
                    SubcomposeAsyncImage(
                        model = repoDetailsViewState.repo.owner.avatar_url,
                        contentDescription = "avatar image",
                        modifier = Modifier
                            .height(50.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        loading = { LoadingState() },
                        error = { ImagePlaceHolder() }
                    )
                }
                // Data texts
                items(repoDetailsViewState.repoDataTexts) {
                    Spacer(modifier = Modifier.height(32.dp))
                    DataTextComponent(dataText = it)
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
                // Button
                item {
                    PrimaryButton("Add to favorites") {
                        favoriteRepositoriesViewModel.addToFavorites(repoDetailsViewState.repo)
                    }
                }
                item { Spacer(modifier = Modifier.height(64.dp)) }
            }
        }
    }
}