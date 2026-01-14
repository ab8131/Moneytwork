package com.example.moneytwork.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moneytwork.core.constants.Constants
import com.example.moneytwork.presentation.market.detail.DetailScreen
import com.example.moneytwork.presentation.market.list.MarketListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MarketList.route,
        modifier = modifier
    ) {
        composable(route = Screen.MarketList.route) {
            MarketListScreen(navController = navController)
        }

        composable(
            route = Screen.CoinDetail.route,
            arguments = listOf(
                navArgument(Constants.PARAM_COIN_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(navController = navController)
        }

        composable(route = Screen.Search.route) {
            // SearchScreen(navController = navController)
        }

        composable(route = Screen.Watchlist.route) {
            // WatchlistScreen(navController = navController)
        }
    }
}

