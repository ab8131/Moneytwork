package com.example.moneytwork.presentation.stocks

import com.example.moneytwork.domain.model.Stock

data class StockListState(
    val isLoading: Boolean = false,
    val stocks: List<Stock> = emptyList(),
    val error: String = ""
)

