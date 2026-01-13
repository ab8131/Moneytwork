package com.example.moneytwork.presentation.market.list

import com.example.moneytwork.domain.model.Coin

data class MarketListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)

