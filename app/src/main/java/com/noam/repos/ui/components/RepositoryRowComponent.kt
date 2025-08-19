package com.noam.repos.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noam.repos.model.domain.RemoteOwner
import com.noam.repos.model.domain.RemoteRepository
import com.noam.repos.ui.theme.ReposSurface

@Composable
fun RepositoryRowComponent(repository: RemoteRepository,
                           width : Dp = 150.dp) {
    val cellModifier = Modifier.width(width).padding(top = 5.dp, bottom = 5.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = repository.owner.login,
            modifier = cellModifier,
            fontSize = 16.sp,
            color = ReposSurface,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = Color.White)
        Text(
            text = repository.name,
            modifier = cellModifier,
            fontSize = 16.sp,
            color = ReposSurface,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = Color.White)
        Text(
            text = repository.owner.avatar_url,
            modifier = cellModifier,
            fontSize = 16.sp,
            color = ReposSurface,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = Color.White)
        Text(
            text = repository.description ?: "No description",
            modifier = cellModifier,
            fontSize = 16.sp,
            color = ReposSurface,
            textAlign = TextAlign.Center
        )
        VerticalSpacerWithLine(lineColor = Color.White)
        Text(
            text = repository.stargazers_count.toString(),
            modifier = cellModifier,
            fontSize = 16.sp,
            color = ReposSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun RepositoryRowComponentPreview() {
    val repository = RemoteRepository(
        id = 28,
        name = "The Repos Mixup",
        owner = RemoteOwner(
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