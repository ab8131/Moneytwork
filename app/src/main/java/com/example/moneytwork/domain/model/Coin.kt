package com.example.moneytwork.domain.model

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val marketCap: Long,
    val marketCapRank: Int?,
    val totalVolume: Long,
    val priceChangePercentage24h: Double?,
    val lastUpdated: String
)

