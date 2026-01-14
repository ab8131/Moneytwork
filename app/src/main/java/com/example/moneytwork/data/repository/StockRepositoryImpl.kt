package com.example.moneytwork.data.repository

import com.example.moneytwork.core.util.Resource
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
    private val api: FinnhubApi
) : StockRepository {

    override suspend fun getStocks(symbols: List<String>): Flow<Resource<List<Stock>>> = flow {
        try {
            emit(Resource.Loading())

            val stocks = symbols.mapNotNull { symbol ->
                try {
                    val quote = api.getStockQuote(symbol)
                    val profile = try {
                        api.getStockProfile(symbol)
                    } catch (e: Exception) {
                        null
                    }

                    val priceChange = quote.currentPrice - quote.previousClose
                    val priceChangePercentage = (priceChange / quote.previousClose) * 100

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
                    )
                } catch (e: Exception) {
                    null
                }
            }

            emit(Resource.Success(stocks))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getStockDetail(symbol: String): Flow<Resource<Stock>> = flow {
        try {
            emit(Resource.Loading())

            val quote = api.getStockQuote(symbol)
            val profile = api.getStockProfile(symbol)

            val priceChange = quote.currentPrice - quote.previousClose
            val priceChangePercentage = (priceChange / quote.previousClose) * 100

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

            emit(Resource.Success(stock))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}

