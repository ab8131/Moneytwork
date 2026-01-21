package com.example.moneytwork.presentation.navigation

sealed class Screen(val route: String) {
    object MarketList : Screen("market_list")
    object CoinDetail : Screen("coin_detail/{coinId}") {
        fun createRoute(coinId: String) = "coin_detail/$coinId"
    }
    object Search : Screen("search")
    object Watchlist : Screen("watchlist")
}

