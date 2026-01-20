package com.example.moneytwork.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.moneytwork.R

sealed class BottomNavItem(
    val route: String,
    val titleRes: Int,
    val icon: ImageVector
) {
    object Portfolio : BottomNavItem("portfolio", R.string.portfolio, Icons.Default.Home)
    object Crypto : BottomNavItem("crypto", R.string.crypto, Icons.Default.Star)
    object Stocks : BottomNavItem("stocks", R.string.stocks, Icons.Default.Notifications)
    object Settings : BottomNavItem("settings", R.string.settings, Icons.Default.Settings)
}

