package com.example.moneytwork.domain.usecase

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.Coin
import com.example.moneytwork.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingCoinsUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<Resource<List<Coin>>> {
        return repository.getCoins(forceRefresh)
    }
}

