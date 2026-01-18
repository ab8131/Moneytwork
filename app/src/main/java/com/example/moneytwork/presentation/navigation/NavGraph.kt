package com.example.moneytwork.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moneytwork.core.constants.Constants
import com.example.moneytwork.presentation.auth.AuthViewModel
import com.example.moneytwork.presentation.auth.SignInScreen
import com.example.moneytwork.presentation.auth.SignUpScreen
import com.example.moneytwork.presentation.community.CommunityScreen
import com.example.moneytwork.presentation.market.detail.DetailScreen
import com.example.moneytwork.presentation.market.list.MarketListScreen
import com.example.moneytwork.presentation.portfolio.PortfolioScreen
import com.example.moneytwork.presentation.search.SearchScreen
import com.example.moneytwork.presentation.stocks.StockListScreen
import com.example.moneytwork.presentation.stocks.detail.StockDetailScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isSignedIn by authViewModel.isUserSignedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isSignedIn) BottomNavItem.Portfolio.route else "sign_in",
        modifier = modifier
    ) {
        // Auth Screens
        composable("sign_in") {
            SignInScreen(navController = navController)
        }

        composable("sign_up") {
            SignUpScreen(navController = navController)
        }

        // Bottom Nav Screens
        composable(BottomNavItem.Portfolio.route) {
            PortfolioScreen(navController = navController)
        }

        composable(BottomNavItem.Crypto.route) {
            MarketListScreen(navController = navController)
        }

        composable(BottomNavItem.Stocks.route) {
            StockListScreen(navController = navController)
        }

        composable(BottomNavItem.Community.route) {
            CommunityScreen()
        }

        // Detail Screens
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

        composable(
            route = "stock_detail/{symbol}",
            arguments = listOf(
                navArgument("symbol") {
                    type = NavType.StringType
                }
            )
        ) {
            StockDetailScreen(navController = navController)
        }

        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable(route = Screen.Watchlist.route) {
            // WatchlistScreen(navController = navController)
        }
    }
}

