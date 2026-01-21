package com.example.moneytwork.presentation.market.list

sealed class MarketListEvent {
    object Refresh : MarketListEvent()
    data class NavigateToDetail(val coinId: String) : MarketListEvent()
    data class OnTabSelected(val tabIndex: Int) : MarketListEvent()
}

