package com.example.moneytwork.presentation.stocks.detail

import com.example.moneytwork.domain.model.Stock

data class StockDetailState(
    val isLoading: Boolean = false,
    val stock: Stock? = null,
    val error: String = ""
)

