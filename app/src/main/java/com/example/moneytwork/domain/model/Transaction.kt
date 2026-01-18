package com.example.moneytwork.domain.model

data class Transaction(
    val id: Long = 0,
    val assetId: String,
    val assetType: AssetType,
    val assetName: String,
    val assetSymbol: String,
    val transactionType: TransactionType,
    val amount: Double,
    val pricePerUnit: Double,
    val totalValue: Double,
    val timestamp: Long,
    val notes: String = ""
)

enum class AssetType {
    CRYPTO,
    STOCK
}

enum class TransactionType {
    BUY,
    SELL
}

data class AssetHolding(
    val assetId: String,
    val assetType: AssetType,
    val assetName: String,
    val assetSymbol: String,
    val totalAmount: Double,
    val averageBuyPrice: Double,
    val totalInvested: Double,
    val currentPrice: Double,
    val currentValue: Double,
    val profitLoss: Double,
    val profitLossPercentage: Double,
    val transactions: List<Transaction> = emptyList()
)

