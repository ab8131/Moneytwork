package com.example.moneytwork.domain.repository

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getStocks(symbols: List<String>): Flow<Resource<List<Stock>>>
    suspend fun getStockDetail(symbol: String): Flow<Resource<Stock>>
}

