package com.example.moneytwork.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StockQuoteDto(
    @SerializedName("c") val currentPrice: Double,
    @SerializedName("h") val highPrice: Double,
    @SerializedName("l") val lowPrice: Double,
    @SerializedName("o") val openPrice: Double,
    @SerializedName("pc") val previousClose: Double,
    @SerializedName("t") val timestamp: Long
)

data class StockSymbolDto(
    @SerializedName("description") val description: String,
    @SerializedName("displaySymbol") val displaySymbol: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("type") val type: String
)

data class StockProfileDto(
    @SerializedName("country") val country: String?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("exchange") val exchange: String?,
    @SerializedName("finnhubIndustry") val industry: String?,
    @SerializedName("logo") val logo: String?,
    @SerializedName("marketCapitalization") val marketCap: Double?,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("shareOutstanding") val sharesOutstanding: Double?,
    @SerializedName("ticker") val ticker: String,
    @SerializedName("weburl") val website: String?
)

