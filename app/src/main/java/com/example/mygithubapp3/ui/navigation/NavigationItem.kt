package com.example.mygithubapp3.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val screen: Screen,
    @StringRes val title: Int,
    val icon: ImageVector,
)
