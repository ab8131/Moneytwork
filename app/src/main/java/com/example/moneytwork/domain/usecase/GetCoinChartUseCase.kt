package com.example.moneytwork.domain.usecase

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.ChartData
import com.example.moneytwork.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinChartUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(coinId: String, days: String): Flow<Resource<ChartData>> {
        return repository.getMarketChart(coinId, days)
    }
}

