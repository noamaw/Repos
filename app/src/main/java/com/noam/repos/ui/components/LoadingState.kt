package com.noam.repos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@Composable
fun LoadingState(modifier: Modifier = defaultModifier) {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize().background(color = MaterialTheme.colorScheme.background)
            .padding(all = 128.dp),
        color = MaterialTheme.colorScheme.onBackground
    )
}