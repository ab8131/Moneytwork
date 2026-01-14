package com.example.moneytwork.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Portfolio : BottomNavItem("portfolio", "Portfolio", Icons.Default.Home)
    object Crypto : BottomNavItem("crypto", "Crypto", Icons.Default.Star)
    object Stocks : BottomNavItem("stocks", "Stocks", Icons.Default.Notifications)
    object Community : BottomNavItem("community", "Community", Icons.Default.AccountCircle)
}

