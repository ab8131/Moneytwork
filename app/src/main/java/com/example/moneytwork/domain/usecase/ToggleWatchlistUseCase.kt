package com.example.moneytwork.domain.usecase

import com.example.moneytwork.domain.repository.CryptoRepository
import javax.inject.Inject

class ToggleWatchlistUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    suspend operator fun invoke(coinId: String) {
        if (repository.isInWatchlist(coinId)) {
            repository.removeFromWatchlist(coinId)
        } else {
            repository.addToWatchlist(coinId)
        }
    }
}

