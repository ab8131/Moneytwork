package com.example.moneytwork.data.remote.api

import com.example.moneytwork.BuildConfig
import com.example.moneytwork.data.remote.dto.StockProfileDto
import com.example.moneytwork.data.remote.dto.StockQuoteDto
import com.example.moneytwork.data.remote.dto.StockSymbolDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FinnhubApi {

    @GET("quote")
    suspend fun getStockQuote(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String = BuildConfig.FINNHUB_API_KEY
    ): StockQuoteDto

    @GET("stock/profile2")
    suspend fun getStockProfile(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String = BuildConfig.FINNHUB_API_KEY
    ): StockProfileDto

    @GET("stock/symbol")
    suspend fun getStockSymbols(
        @Query("exchange") exchange: String = "US",
        @Query("token") apiKey: String = BuildConfig.FINNHUB_API_KEY
    ): List<StockSymbolDto>
}

