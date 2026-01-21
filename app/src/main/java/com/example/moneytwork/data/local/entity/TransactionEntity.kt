package com.example.moneytwork.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val assetId: String, // coin id or stock symbol
    val assetType: String, // "CRYPTO" or "STOCK"
    val assetName: String,
    val assetSymbol: String,
    val transactionType: String, // "BUY" or "SELL"
    val amount: Double, // quantity of asset
    val pricePerUnit: Double, // price at the time of transaction
    val totalValue: Double, // amount * pricePerUnit
    val timestamp: Long = System.currentTimeMillis(),
    val notes: String = ""
)

