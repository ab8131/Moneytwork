package com.example.moneytwork.domain.model

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for domain models
 * Tests our Transaction and AssetType enums work correctly
 */
class DomainModelTest {

    @Test
    fun `TransactionType enum has BUY and SELL`() {
        // Test that our TransactionType enum is defined correctly
        assertEquals(TransactionType.BUY, TransactionType.valueOf("BUY"))
        assertEquals(TransactionType.SELL, TransactionType.valueOf("SELL"))
    }

    @Test
    fun `AssetType enum has CRYPTO and STOCK`() {
        // Test that our AssetType enum is defined correctly
        assertEquals(AssetType.CRYPTO, AssetType.valueOf("CRYPTO"))
        assertEquals(AssetType.STOCK, AssetType.valueOf("STOCK"))
    }

    @Test
    fun `Transaction model creates with correct types`() {
        // Test creating a Transaction with enums
        val transaction = Transaction(
            id = 1,
            assetId = "bitcoin",
            assetType = AssetType.CRYPTO,
            assetName = "Bitcoin",
            assetSymbol = "BTC",
            transactionType = TransactionType.BUY,
            amount = 0.5,
            pricePerUnit = 50000.0,
            totalValue = 25000.0,
            timestamp = System.currentTimeMillis(),
            notes = "Test transaction"
        )

        assertEquals(AssetType.CRYPTO, transaction.assetType)
        assertEquals(TransactionType.BUY, transaction.transactionType)
        assertEquals("bitcoin", transaction.assetId)
        assertEquals(0.5, transaction.amount, 0.001)
    }

    @Test
    fun `Transaction supports both BUY and SELL operations`() {
        // Test BUY transaction
        val buyTransaction = Transaction(
            id = 1,
            assetId = "bitcoin",
            assetType = AssetType.CRYPTO,
            assetName = "Bitcoin",
            assetSymbol = "BTC",
            transactionType = TransactionType.BUY,
            amount = 0.1,
            pricePerUnit = 50000.0,
            totalValue = 5000.0,
            timestamp = System.currentTimeMillis()
        )

        assertEquals(TransactionType.BUY, buyTransaction.transactionType)

        // Test SELL transaction
        val sellTransaction = buyTransaction.copy(
            id = 2,
            transactionType = TransactionType.SELL
        )

        assertEquals(TransactionType.SELL, sellTransaction.transactionType)
    }

    @Test
    fun `AssetHolding model stores portfolio data correctly`() {
        // Test that AssetHolding can store complete portfolio information
        val holding = AssetHolding(
            assetId = "bitcoin",
            assetType = AssetType.CRYPTO,
            assetName = "Bitcoin",
            assetSymbol = "BTC",
            totalAmount = 0.5,
            averageBuyPrice = 45000.0,
            totalInvested = 22500.0,
            currentPrice = 50000.0,
            currentValue = 25000.0,
            profitLoss = 2500.0,
            profitLossPercentage = 11.11,
            transactions = emptyList()
        )

        assertEquals("bitcoin", holding.assetId)
        assertEquals(AssetType.CRYPTO, holding.assetType)
        assertEquals(0.5, holding.totalAmount, 0.001)
        assertEquals(45000.0, holding.averageBuyPrice, 0.01)
        assertTrue("Profit should be positive", holding.profitLoss > 0)
    }
}

