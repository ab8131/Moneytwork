package com.example.moneytwork.domain.usecase

import com.example.moneytwork.domain.model.Coin
import com.example.moneytwork.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchlistUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(): Flow<List<Coin>> {
        return repository.getWatchlist()
    }
}

