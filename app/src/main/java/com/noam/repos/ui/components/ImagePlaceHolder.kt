package com.noam.repos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.noam.repos.R

@Composable
fun ImagePlaceHolder() {
    Image(
        painter = (painterResource(id = R.drawable.image_place_holder)), // Replace with your image painter
        contentDescription = "Placeholder Image",
        modifier = androidx.compose.ui.Modifier,
        contentScale = androidx.compose.ui.layout.ContentScale.Crop
    )
}