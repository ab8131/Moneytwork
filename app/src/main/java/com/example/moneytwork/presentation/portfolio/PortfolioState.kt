package com.example.moneytwork.presentation.portfolio

data class PortfolioState(
    val isLoading: Boolean = false,
    val totalInvestment: Double = 0.0,
    val currentValue: Double = 0.0,
    val profitLoss: Double = 0.0,
    val profitLossPercentage: Double = 0.0
)

