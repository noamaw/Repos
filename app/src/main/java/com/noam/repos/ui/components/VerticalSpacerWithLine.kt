package com.noam.repos.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpacerWithLine(
    width : Dp = 4.dp,
    thickness: Dp = 1.dp,
    lineColor: Color = Color.LightGray
) {
    Spacer(modifier = Modifier.width(width))
    VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = thickness, color = lineColor)
    Spacer(modifier = Modifier.width(width))
}