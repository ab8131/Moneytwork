package com.example.moneytwork.domain.repository

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.ChartData
import com.example.moneytwork.domain.model.Coin
import com.example.moneytwork.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getCoins(forceRefresh: Boolean = false): Flow<Resource<List<Coin>>>
    fun getCoinDetail(coinId: String): Flow<Resource<CoinDetail>>
    fun getMarketChart(coinId: String, days: String): Flow<Resource<ChartData>>
    fun searchCoins(query: String): Flow<Resource<List<Coin>>>
    fun getWatchlist(): Flow<List<Coin>>
    suspend fun addToWatchlist(coinId: String)
    suspend fun removeFromWatchlist(coinId: String)
    suspend fun isInWatchlist(coinId: String): Boolean
}

