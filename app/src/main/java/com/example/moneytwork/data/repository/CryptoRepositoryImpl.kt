package com.example.moneytwork.data.repository

import android.util.Log
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.data.local.dao.CoinDao
import com.example.moneytwork.data.local.dao.WatchlistDao
import com.example.moneytwork.data.local.entity.CoinEntity
import com.example.moneytwork.data.local.entity.WatchlistEntity
import com.example.moneytwork.data.remote.api.CoinGeckoApi
import com.example.moneytwork.data.remote.dto.CoinDetailDto
import com.example.moneytwork.data.remote.dto.CoinDto
import com.example.moneytwork.data.remote.dto.MarketChartDto
import com.example.moneytwork.domain.model.ChartData
import com.example.moneytwork.domain.model.ChartPoint
import com.example.moneytwork.domain.model.Coin
import com.example.moneytwork.domain.model.CoinDetail
import com.example.moneytwork.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val coinDao: CoinDao,
    private val watchlistDao: WatchlistDao
) : CryptoRepository {

    override fun getCoins(forceRefresh: Boolean): Flow<Resource<List<Coin>>> = flow {
        Log.d("CryptoRepository", "getCoins called")
        emit(Resource.Loading())

        // Fetch from API
        try {
            Log.d("CryptoRepository", "Fetching from API...")
            val remoteCoins = api.getMarketCoins()
            Log.d("CryptoRepository", "API returned ${remoteCoins.size} coins")
            val entities = remoteCoins.map { it.toEntity() }
            coinDao.clearCoins()
            coinDao.insertCoins(entities)

            emit(Resource.Success(entities.map { it.toCoin() }))
            Log.d("CryptoRepository", "Success emitted")
        } catch (e: Exception) {
            Log.e("CryptoRepository", "Error: ${e.message}", e)
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getCoinDetail(coinId: String): Flow<Resource<CoinDetail>> = flow {
        emit(Resource.Loading())

        try {
            val detail = api.getCoinDetail(coinId)
            emit(Resource.Success(detail.toCoinDetail()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getMarketChart(coinId: String, days: String): Flow<Resource<ChartData>> = flow {
        emit(Resource.Loading())

        try {
            val chart = api.getMarketChart(coinId = coinId, days = days)
            emit(Resource.Success(chart.toChartData()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun searchCoins(query: String): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())

        try {
            val searchResult = api.searchCoins(query)
            val coins = searchResult.coins.map { searchCoin ->
                Coin(
                    id = searchCoin.id,
                    symbol = searchCoin.symbol.uppercase(),
                    name = searchCoin.name,
                    image = searchCoin.large ?: "",
                    currentPrice = 0.0,
                    marketCap = 0L,
                    marketCapRank = searchCoin.marketCapRank,
                    totalVolume = 0L,
                    priceChangePercentage24h = null,
                    lastUpdated = ""
                )
            }
            emit(Resource.Success(coins))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getWatchlist(): Flow<List<Coin>> {
        return watchlistDao.getWatchlist().map { watchlistItems ->
            watchlistItems.mapNotNull { item ->
                coinDao.getCoinById(item.coinId)?.toCoin()
            }
        }
    }

    override suspend fun addToWatchlist(coinId: String) {
        watchlistDao.addToWatchlist(WatchlistEntity(coinId))
    }

    override suspend fun removeFromWatchlist(coinId: String) {
        watchlistDao.removeFromWatchlistById(coinId)
    }

    override suspend fun isInWatchlist(coinId: String): Boolean {
        return watchlistDao.getWatchlistItem(coinId) != null
    }
}

// Mapper Extensions
private fun CoinDto.toEntity() = CoinEntity(
    id = id,
    symbol = symbol.uppercase(),
    name = name,
    image = image,
    currentPrice = currentPrice,
    marketCap = marketCap,
    marketCapRank = marketCapRank,
    totalVolume = totalVolume,
    priceChangePercentage24h = priceChangePercentage24h,
    lastUpdated = System.currentTimeMillis()
)

private fun CoinEntity.toCoin() = Coin(
    id = id,
    symbol = symbol,
    name = name,
    image = image,
    currentPrice = currentPrice,
    marketCap = marketCap,
    marketCapRank = marketCapRank,
    totalVolume = totalVolume,
    priceChangePercentage24h = priceChangePercentage24h,
    lastUpdated = ""
)

private fun CoinDetailDto.toCoinDetail() = CoinDetail(
    id = id,
    symbol = symbol.uppercase(),
    name = name,
    description = description?.get("en") ?: "",
    image = image?.large ?: "",
    currentPrice = marketData?.currentPrice?.get("usd") ?: 0.0,
    marketCap = marketData?.marketCap?.get("usd") ?: 0L,
    totalVolume = marketData?.totalVolume?.get("usd") ?: 0L,
    high24h = marketData?.high24h?.get("usd"),
    low24h = marketData?.low24h?.get("usd"),
    priceChangePercentage24h = marketData?.priceChangePercentage24h,
    priceChangePercentage7d = marketData?.priceChangePercentage7d,
    priceChangePercentage30d = marketData?.priceChangePercentage30d
)

private fun MarketChartDto.toChartData() = ChartData(
    prices = prices.map {
        ChartPoint(
            timestamp = it[0].toLong(),
            price = it[1]
        )
    }
)

