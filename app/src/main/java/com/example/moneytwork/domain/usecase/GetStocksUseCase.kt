package com.example.moneytwork.domain.usecase

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.Stock
import com.example.moneytwork.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStocksUseCase @Inject constructor(
    private val repository: StockRepository
) {
    suspend operator fun invoke(symbols: List<String>): Flow<Resource<List<Stock>>> {
        return repository.getStocks(symbols)
    }
}

