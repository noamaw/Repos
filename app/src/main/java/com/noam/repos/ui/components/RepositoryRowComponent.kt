package com.noam.repos.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.noam.repos.model.domain.Owner
import com.noam.repos.model.domain.GitRepository

@Composable
fun RepositoryRowComponent(repository: GitRepository,
                           width : Dp = 150.dp,
                           onRowClick: (GitRepository) -> Unit = {},
                           onFavClick: (GitRepository) -> Unit = {}) {
    val cellModifier = Modifier.width(width).padding(top = 5.dp, bottom = 5.dp, start = 2.dp, end = 2.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable {
                onRowClick(repository)
            }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (repository.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Favorite Icon",
            modifier = Modifier
                .width(50.dp)
                .padding(top = 5.dp, bottom = 5.dp, start = 2.dp, end = 2.dp)
                .clickable {
                onFavClick(repository)
            },
            tint = if (repository.isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground
        )
        VerticalSpacerWithLine(lineColor = MaterialTheme.colorScheme.primary)
        Text(
            text = repository.owner.login,
            modifier = cellModifier,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = MaterialTheme.colorScheme.primary)
        Text(
            text = repository.name,
            modifier = cellModifier,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = MaterialTheme.colorScheme.primary)
        Box(
            modifier = Modifier
                .width(150.dp),
            contentAlignment = Alignment.Center
        ) {
                    SubcomposeAsyncImage(
                        model = repository.owner.avatar_url,
                        contentDescription = "avatar image",
                        modifier = Modifier
                            .height(50.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        loading = { LoadingState() },
                        error = { ImagePlaceHolder()}
                    )
                }
        VerticalSpacerWithLine(lineColor = MaterialTheme.colorScheme.primary)
        Text(
            text = repository.description ?: "No description",
            modifier = cellModifier,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = MaterialTheme.colorScheme.primary)
        Text(
            text = repository.stargazers_count.toString(),
            modifier = cellModifier,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun RepositoryRowComponentPreview() {
    val repository = GitRepository(
        id = 28,
        name = "The Repos Mixup",
        owner = Owner(
            id = 1,
            login = "Noam fdalh vjah",
            avatar_url = "https://avatars.githubusercontent.com/u/1?v=4"
        ),
        description = "A collection of repositories for the Repos project.",
        stargazers_count = 42,
        language = "Kotlin",
        private = false,
        forks = 3,
        created_at = "2023-01-01T00:00:00Z",
        html_url = "github.com/noam/repos-mixup",
    )

    RepositoryRowComponent(repository = repository)
}