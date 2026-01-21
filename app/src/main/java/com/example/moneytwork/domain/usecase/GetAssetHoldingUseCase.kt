package com.example.moneytwork.domain.usecase

import com.example.moneytwork.domain.model.AssetHolding
import com.example.moneytwork.domain.repository.PortfolioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAssetHoldingUseCase @Inject constructor(
    private val repository: PortfolioRepository
) {
    operator fun invoke(assetId: String): Flow<AssetHolding?> {
        return repository.getHoldingByAsset(assetId)
    }
}

