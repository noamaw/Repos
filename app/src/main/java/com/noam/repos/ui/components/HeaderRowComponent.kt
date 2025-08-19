package com.noam.repos.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val TableHeaders = listOf(
    "Username",
    "Repository Name",
    "User Avatar",
    "Description",
    "Stars",
)

@Composable
fun HeaderRowComponent(width: Dp = 150.dp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = Color.Black)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically) {
        TableHeaders.forEachIndexed { index, header ->
            Text(
                text = header,
                modifier = Modifier
                    .width(width)
                    .padding(8.dp),
                fontSize = 18.sp,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            if (index < TableHeaders.size - 1) {
                VerticalSpacerWithLine(
                    lineColor = Color.White
                )
            }
        }
    }
}