package com.example.moneytwork.domain.usecase

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.Coin
import com.example.moneytwork.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCoinsUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Coin>>> {
        if (query.isBlank()) {
            return kotlinx.coroutines.flow.flowOf(Resource.Success(emptyList()))
        }
        return repository.searchCoins(query)
    }
}

