package com.example.moneytwork.domain.usecase

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.CoinDetail
import com.example.moneytwork.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CryptoRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> {
        return repository.getCoinDetail(coinId)
    }
}

