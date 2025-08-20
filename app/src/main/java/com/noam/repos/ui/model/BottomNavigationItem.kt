package com.noam.repos.ui.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.noam.repos.ui.screens.BottomNavigationTitle

data class BottomNavigationItem(
    val bottomNavigationTitle: BottomNavigationTitle,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null,
)