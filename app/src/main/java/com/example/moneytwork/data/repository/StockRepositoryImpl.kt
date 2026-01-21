package com.example.moneytwork.data.repository

import android.util.Log
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.data.local.dao.StockDao
import com.example.moneytwork.data.local.entity.StockEntity
import com.example.moneytwork.data.remote.api.FinnhubApi
import com.example.moneytwork.domain.model.Stock
import com.example.moneytwork.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: FinnhubApi,
    private val stockDao: StockDao
) : StockRepository {

    override suspend fun getStocks(symbols: List<String>): Flow<Resource<List<Stock>>> = flow {
        Log.d("StockRepository", "=== getStocks START ===")
        Log.d("StockRepository", "Fetching ${symbols.size} stocks")
        
        try {
            emit(Resource.Loading())

            // First, try to get cached data
            val cachedStocks = mutableListOf<Stock>()
            symbols.forEach { symbol ->
                try {
                    stockDao.getStockBySymbol(symbol)?.let { cached ->
                        cachedStocks.add(cached.toStock())
                        Log.d("StockRepository", "✓ Cached: $symbol")
                    }
                } catch (e: Exception) {
                    Log.e("StockRepository", "✗ Cache error for $symbol: ${e.message}")
                }
            }
            
            // Emit cached data if available (for instant display)
            if (cachedStocks.isNotEmpty()) {
                Log.d("StockRepository", "Emitting ${cachedStocks.size} cached stocks")
                emit(Resource.Success(cachedStocks))
            }

            // Fetch fresh data from API
            Log.d("StockRepository", "Fetching fresh data from API...")
            val stocks = symbols.mapNotNull { symbol ->
                try {
                    Log.d("StockRepository", "Fetching quote for $symbol...")
                    val quote = api.getStockQuote(symbol)
                    Log.d("StockRepository", "✓ Quote for $symbol: ${quote.currentPrice}")
                    
                    val profile = try {
                        Log.d("StockRepository", "Fetching profile for $symbol...")
                        api.getStockProfile(symbol)
                    } catch (e: Exception) {
                        Log.w("StockRepository", "Profile failed for $symbol: ${e.message}")
                        null
                    }

                    val priceChange = quote.currentPrice - quote.previousClose
                    val priceChangePercentage = if (quote.previousClose > 0) {
                        (priceChange / quote.previousClose) * 100
                    } else {
                        Log.w("StockRepository", "previousClose is 0 for $symbol")
                        0.0
                    }

                    Stock(
                        symbol = symbol,
                        name = profile?.name ?: symbol,
                        currentPrice = quote.currentPrice,
                        priceChange = priceChange,
                        priceChangePercentage = priceChangePercentage,
                        highPrice = quote.highPrice,
                        lowPrice = quote.lowPrice,
                        openPrice = quote.openPrice,
                        previousClose = quote.previousClose,
                        marketCap = profile?.marketCap,
                        logo = profile?.logo,
                        exchange = profile?.exchange
                    ).also {
                        Log.d("StockRepository", "✓ Created stock object for $symbol")
                    }
                } catch (e: Exception) {
                    Log.e("StockRepository", "✗ Failed to fetch $symbol: ${e.message}")
                    null
                }
            }

            // Cache the fresh data
            if (stocks.isNotEmpty()) {
                Log.d("StockRepository", "Caching ${stocks.size} stocks...")
                stocks.forEach { stock ->
                    try {
                        stockDao.insertStock(stock.toEntity())
                    } catch (e: Exception) {
                        Log.e("StockRepository", "Cache insert failed for ${stock.symbol}: ${e.message}")
                    }
                }
                emit(Resource.Success(stocks))
                Log.d("StockRepository", "✓ Emitted ${stocks.size} fresh stocks")
            } else if (cachedStocks.isEmpty()) {
                Log.e("StockRepository", "✗ No stocks loaded (fresh or cached)")
                emit(Resource.Error("Failed to load stocks"))
            }
        } catch (e: HttpException) {
            Log.e("StockRepository", "✗ HTTP Error: ${e.code()} - ${e.message()}")
            emit(Resource.Error("API Error: ${e.message()}"))
        } catch (e: IOException) {
            Log.e("StockRepository", "✗ Network Error: ${e.message}")
            emit(Resource.Error("Network error. Check your connection."))
        } catch (e: Exception) {
            Log.e("StockRepository", "✗ Unexpected Error: ${e.javaClass.simpleName}")
            e.printStackTrace()
            emit(Resource.Error("Error: ${e.message}"))
        }
        
        Log.d("StockRepository", "=== getStocks END ===")
    }

    override suspend fun getStockDetail(symbol: String): Flow<Resource<Stock>> = flow {
        Log.d("StockRepository", "=== getStockDetail START for $symbol ===")
        
        try {
            emit(Resource.Loading())

            // First emit cached data if available
            val cachedStock = stockDao.getStockBySymbol(symbol)
            if (cachedStock != null) {
                Log.d("StockRepository", "✓ Found cached stock for $symbol")
                emit(Resource.Success(cachedStock.toStock()))
            }

            // Then fetch fresh data
            Log.d("StockRepository", "Fetching fresh quote for $symbol...")
            val quote = api.getStockQuote(symbol)
            Log.d("StockRepository", "✓ Quote received: ${quote.currentPrice}")
            
            Log.d("StockRepository", "Fetching profile for $symbol...")
            val profile = api.getStockProfile(symbol)
            Log.d("StockRepository", "✓ Profile received: ${profile.name}")

            val priceChange = quote.currentPrice - quote.previousClose
            val priceChangePercentage = if (quote.previousClose > 0) {
                (priceChange / quote.previousClose) * 100
            } else {
                0.0
            }

            val stock = Stock(
                symbol = symbol,
                name = profile.name,
                currentPrice = quote.currentPrice,
                priceChange = priceChange,
                priceChangePercentage = priceChangePercentage,
                highPrice = quote.highPrice,
                lowPrice = quote.lowPrice,
                openPrice = quote.openPrice,
                previousClose = quote.previousClose,
                marketCap = profile.marketCap,
                logo = profile.logo,
                exchange = profile.exchange
            )

            // Cache the fresh stock
            stockDao.insertStock(stock.toEntity())
            Log.d("StockRepository", "✓ Cached stock for $symbol")
            
            emit(Resource.Success(stock))
            Log.d("StockRepository", "✓ Emitted fresh stock for $symbol")
        } catch (e: HttpException) {
            Log.e("StockRepository", "✗ HTTP Error for $symbol: ${e.code()} - ${e.message()}")
            emit(Resource.Error("API Error: ${e.message()}"))
        } catch (e: IOException) {
            Log.e("StockRepository", "✗ Network Error for $symbol: ${e.message}")
            emit(Resource.Error("Network error. Check your connection."))
        } catch (e: Exception) {
            Log.e("StockRepository", "✗ Unexpected Error for $symbol: ${e.javaClass.simpleName} - ${e.message}")
            e.printStackTrace()
            emit(Resource.Error("Error loading stock: ${e.message}"))
        }
        
        Log.d("StockRepository", "=== getStockDetail END for $symbol ===")
    }
}

// Extension functions for Stock <-> StockEntity mapping
private fun Stock.toEntity(): StockEntity {
    return StockEntity(
        symbol = symbol,
        name = name,
        currentPrice = currentPrice,
        priceChange = priceChange,
        priceChangePercentage = priceChangePercentage,
        highPrice = highPrice,
        lowPrice = lowPrice,
        openPrice = openPrice,
        previousClose = previousClose,
        marketCap = marketCap,
        logo = logo,
        exchange = exchange
    )
}

private fun StockEntity.toStock(): Stock {
    return Stock(
        symbol = symbol,
        name = name,
        currentPrice = currentPrice,
        priceChange = priceChange,
        priceChangePercentage = priceChangePercentage,
        highPrice = highPrice,
        lowPrice = lowPrice,
        openPrice = openPrice,
        previousClose = previousClose,
        marketCap = marketCap,
        logo = logo,
        exchange = exchange
    )
}

