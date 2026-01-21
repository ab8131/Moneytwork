package com.example.moneytwork.domain.model

data class Stock(
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val priceChange: Double,
    val priceChangePercentage: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val previousClose: Double,
    val marketCap: Double?,
    val logo: String?,
    val exchange: String?
)

