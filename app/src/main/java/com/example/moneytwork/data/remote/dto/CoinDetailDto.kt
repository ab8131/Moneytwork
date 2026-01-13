package com.example.moneytwork.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: Map<String, String>?,
    @SerializedName("image") val image: ImageDto?,
    @SerializedName("market_data") val marketData: MarketDataDto?
)

data class ImageDto(
    @SerializedName("large") val large: String?
)

data class MarketDataDto(
    @SerializedName("current_price") val currentPrice: Map<String, Double>?,
    @SerializedName("market_cap") val marketCap: Map<String, Long>?,
    @SerializedName("total_volume") val totalVolume: Map<String, Long>?,
    @SerializedName("high_24h") val high24h: Map<String, Double>?,
    @SerializedName("low_24h") val low24h: Map<String, Double>?,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24h: Double?,
    @SerializedName("price_change_percentage_7d") val priceChangePercentage7d: Double?,
    @SerializedName("price_change_percentage_30d") val priceChangePercentage30d: Double?
)

