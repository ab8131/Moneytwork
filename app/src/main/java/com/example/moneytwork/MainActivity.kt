package com.example.moneytwork

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneytwork.core.utils.LanguageUtils
import com.example.moneytwork.data.preferences.LanguagePreferences
import com.example.moneytwork.presentation.navigation.BottomNavItem
import com.example.moneytwork.presentation.navigation.NavGraph
import com.example.moneytwork.ui.theme.MoneytworkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var languagePreferences: LanguagePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved language preference
        lifecycleScope.launch {
            val savedLanguage = languagePreferences.selectedLanguage.first()
            LanguageUtils.setAppLanguage(this@MainActivity, savedLanguage)
        }

        enableEdgeToEdge()
        setContent {
            MoneytworkTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val currentRoute = currentDestination?.route

                val bottomNavItems = listOf(
                    BottomNavItem.Portfolio,
                    BottomNavItem.Crypto,
                    BottomNavItem.Stocks,
                    BottomNavItem.Settings
                )

                // Hide bottom bar on auth screens
                val showBottomBar = currentRoute !in listOf("sign_in", "sign_up")

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        if (showBottomBar) {
                            Surface(
                                color = Color.Transparent,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color(0x15FFFFFF),
                                                Color(0x25FFFFFF)
                                            )
                                        )
                                    )
                            ) {
                                NavigationBar(
                                    containerColor = Color.Transparent
                                ) {
                                    bottomNavItems.forEach { item ->
                                        NavigationBarItem(
                                            icon = {
                                                Icon(
                                                    item.icon,
                                                    contentDescription = stringResource(item.titleRes)
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = stringResource(item.titleRes),
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                                    maxLines = 1
                                                )
                                            },
                                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                            alwaysShowLabel = true,
                                            onClick = {
                                                navController.navigate(item.route) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                                                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                                                indicatorColor = Color.White.copy(alpha = 0.1f)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) { paddingValues ->
                    NavGraph(
                        navController = navController,
                        modifier = if (showBottomBar) Modifier.padding(paddingValues) else Modifier
                    )
                }
            }
        }
    }
}
