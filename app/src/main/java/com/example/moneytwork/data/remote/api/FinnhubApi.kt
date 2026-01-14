package com.example.moneytwork.data.remote.api

import com.example.moneytwork.data.remote.dto.StockProfileDto
import com.example.moneytwork.data.remote.dto.StockQuoteDto
import com.example.moneytwork.data.remote.dto.StockSymbolDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {

    @GET("quote")
    suspend fun getStockQuote(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String = "YOUR_API_KEY_HERE" // You'll need to get a free API key from finnhub.io
    ): StockQuoteDto

    @GET("stock/profile2")
    suspend fun getStockProfile(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String = "YOUR_API_KEY_HERE"
    ): StockProfileDto

    @GET("stock/symbol")
    suspend fun getStockSymbols(
        @Query("exchange") exchange: String = "US",
        @Query("token") apiKey: String = "YOUR_API_KEY_HERE"
    ): List<StockSymbolDto>
}

