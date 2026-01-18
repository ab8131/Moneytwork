package com.example.moneytwork.domain.repository

import com.example.moneytwork.domain.model.AssetHolding
import com.example.moneytwork.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {

    fun getAllTransactions(): Flow<List<Transaction>>

    fun getTransactionsByAsset(assetId: String): Flow<List<Transaction>>

    suspend fun addTransaction(transaction: Transaction): Long

    suspend fun deleteTransaction(transactionId: Long)

    fun getAllHoldings(): Flow<List<AssetHolding>>

    fun getHoldingByAsset(assetId: String): Flow<AssetHolding?>

    suspend fun calculatePortfolioValue(
        holdings: List<AssetHolding>
    ): PortfolioSummary
}

data class PortfolioSummary(
    val totalInvestment: Double,
    val currentValue: Double,
    val profitLoss: Double,
    val profitLossPercentage: Double
)

