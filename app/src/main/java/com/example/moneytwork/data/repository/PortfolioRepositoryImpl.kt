package com.example.moneytwork.data.repository

import com.example.moneytwork.data.local.dao.TransactionDao
import com.example.moneytwork.data.local.entity.TransactionEntity
import com.example.moneytwork.domain.model.*
import com.example.moneytwork.domain.repository.CryptoRepository
import com.example.moneytwork.domain.repository.PortfolioRepository
import com.example.moneytwork.domain.repository.PortfolioSummary
import com.example.moneytwork.domain.repository.StockRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val cryptoRepository: CryptoRepository,
    private val stockRepository: StockRepository
) : PortfolioRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toTransaction() }
        }
    }

    override fun getTransactionsByAsset(assetId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByAsset(assetId).map { entities ->
            entities.map { it.toTransaction() }
        }
    }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transactionId: Long) {
        transactionDao.deleteTransactionById(transactionId)
    }

    override fun getAllHoldings(): Flow<List<AssetHolding>> {
        return transactionDao.getAllTransactions().flatMapLatest { transactions ->
            if (transactions.isEmpty()) {
                flowOf(emptyList())
            } else {
                val groupedHoldings = transactions.groupBy { it.assetId }

                // Create flows for each holding with current price
                val holdingFlows = groupedHoldings.map { (assetId, assetTransactions) ->
                    val firstTx = assetTransactions.firstOrNull()
                    when (firstTx?.assetType) {
                        "CRYPTO" -> {
                            cryptoRepository.getCoins(false).map { resource ->
                                resource.data?.find { it.id == assetId }?.currentPrice ?: 0.0
                            }.map { currentPrice ->
                                calculateAssetHolding(assetId, assetTransactions, currentPrice)
                            }
                        }
                        else -> flowOf(calculateAssetHolding(assetId, assetTransactions, 0.0))
                    }
                }

                // Combine all holding flows
                combine(holdingFlows) { holdings ->
                    holdings.toList().filter { it.totalAmount > 0 }
                }
            }
        }
    }

    override fun getHoldingByAsset(assetId: String): Flow<AssetHolding?> {
        return transactionDao.getTransactionsByAsset(assetId).flatMapLatest { transactions ->
            if (transactions.isEmpty()) {
                flowOf(null)
            } else {
                val firstTx = transactions.firstOrNull()
                when (firstTx?.assetType) {
                    "CRYPTO" -> {
                        cryptoRepository.getCoins(false).map { resource ->
                            resource.data?.find { it.id == assetId }?.currentPrice ?: 0.0
                        }.map { currentPrice ->
                            calculateAssetHolding(assetId, transactions, currentPrice)
                        }
                    }
                    else -> flowOf(calculateAssetHolding(assetId, transactions, 0.0))
                }
            }
        }
    }

    override suspend fun calculatePortfolioValue(holdings: List<AssetHolding>): PortfolioSummary {
        val totalInvestment = holdings.sumOf { it.totalInvested }
        val currentValue = holdings.sumOf { it.currentValue }
        val profitLoss = currentValue - totalInvestment
        val profitLossPercentage = if (totalInvestment > 0) {
            (profitLoss / totalInvestment) * 100
        } else {
            0.0
        }

        return PortfolioSummary(
            totalInvestment = totalInvestment,
            currentValue = currentValue,
            profitLoss = profitLoss,
            profitLossPercentage = profitLossPercentage
        )
    }

    private fun calculateAssetHolding(
        assetId: String,
        transactions: List<TransactionEntity>,
        currentPrice: Double
    ): AssetHolding {
        var totalAmount = 0.0
        var totalInvested = 0.0

        transactions.forEach { transaction ->
            when (transaction.transactionType) {
                "BUY" -> {
                    totalAmount += transaction.amount
                    totalInvested += transaction.totalValue
                }
                "SELL" -> {
                    if (totalAmount > 0) {
                        val sellRatio = transaction.amount / totalAmount
                        totalInvested -= totalInvested * sellRatio
                        totalAmount -= transaction.amount
                    }
                }
            }
        }

        val averageBuyPrice = if (totalAmount > 0) totalInvested / totalAmount else 0.0
        val currentValue = totalAmount * currentPrice
        val profitLoss = currentValue - totalInvested
        val profitLossPercentage = if (totalInvested > 0) {
            (profitLoss / totalInvested) * 100
        } else {
            0.0
        }

        val firstTransaction = transactions.firstOrNull()

        return AssetHolding(
            assetId = assetId,
            assetType = AssetType.valueOf(firstTransaction?.assetType ?: "CRYPTO"),
            assetName = firstTransaction?.assetName ?: "",
            assetSymbol = firstTransaction?.assetSymbol ?: "",
            totalAmount = totalAmount,
            averageBuyPrice = averageBuyPrice,
            totalInvested = totalInvested,
            currentPrice = currentPrice,
            currentValue = currentValue,
            profitLoss = profitLoss,
            profitLossPercentage = profitLossPercentage,
            transactions = transactions.map { it.toTransaction() }
        )
    }
}

// Extension functions for mapping
private fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        id = id,
        assetId = assetId,
        assetType = AssetType.valueOf(assetType),
        assetName = assetName,
        assetSymbol = assetSymbol,
        transactionType = TransactionType.valueOf(transactionType),
        amount = amount,
        pricePerUnit = pricePerUnit,
        totalValue = totalValue,
        timestamp = timestamp,
        notes = notes
    )
}

private fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        assetId = assetId,
        assetType = assetType.name,
        assetName = assetName,
        assetSymbol = assetSymbol,
        transactionType = transactionType.name,
        amount = amount,
        pricePerUnit = pricePerUnit,
        totalValue = totalValue,
        timestamp = timestamp,
        notes = notes
    )
}

