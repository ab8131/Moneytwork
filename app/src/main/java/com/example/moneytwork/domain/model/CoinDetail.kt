package com.example.moneytwork.domain.model

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val description: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val totalVolume: Long,
    val high24h: Double?,
    val low24h: Double?,
    val priceChangePercentage24h: Double?,
    val priceChangePercentage7d: Double?,
    val priceChangePercentage30d: Double?
)

