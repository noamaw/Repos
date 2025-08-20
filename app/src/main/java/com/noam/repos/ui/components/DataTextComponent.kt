package com.noam.repos.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class DataText(
    val title: String,
    val text: String
)

@Composable
fun DataTextComponent(dataText: DataText, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = dataText.title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = modifier,
            text = dataText.text,
            fontSize = 18.sp,
            color = if (dataText.title == "Link") Color.Companion.Blue else MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun DataComponentPreview() {
    val data = DataText(title = "Name", text = "Fonny mantos")
    DataTextComponent(dataText = data)
}