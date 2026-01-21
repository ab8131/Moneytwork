package com.example.moneytwork.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class StockEntity(
    @PrimaryKey val symbol: String,
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
    val exchange: String?,
    val lastUpdated: Long = System.currentTimeMillis()
)

