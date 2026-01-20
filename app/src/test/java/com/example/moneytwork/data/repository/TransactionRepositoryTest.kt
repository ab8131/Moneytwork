package com.example.moneytwork.data.repository

import com.example.moneytwork.data.local.entity.TransactionEntity
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for Transaction data model
 * Tests that our transaction entity works correctly
 */
class TransactionEntityTest {

    @Test
    fun `transaction entity creates correctly with all fields`() {
        // Test creating a BUY transaction
        val transaction = TransactionEntity(
            id = 1,
            assetId = "bitcoin",
            assetType = "CRYPTO",
            assetName = "Bitcoin",
            assetSymbol = "BTC",
            transactionType = "BUY",
            amount = 0.5,
            pricePerUnit = 50000.0,
            totalValue = 25000.0,
            timestamp = System.currentTimeMillis()
        )

        assertEquals("bitcoin", transaction.assetId)
        assertEquals("CRYPTO", transaction.assetType)
        assertEquals("BTC", transaction.assetSymbol)
        assertEquals("BUY", transaction.transactionType)
        assertEquals(0.5, transaction.amount, 0.001)
        assertEquals(50000.0, transaction.pricePerUnit, 0.01)
        assertEquals(25000.0, transaction.totalValue, 0.01)
    }

    @Test
    fun `transaction entity supports SELL operations`() {
        // Test creating a SELL transaction
        val transaction = TransactionEntity(
            id = 2,
            assetId = "ethereum",
            assetType = "CRYPTO",
            assetName = "Ethereum",
            assetSymbol = "ETH",
            transactionType = "SELL",
            amount = 1.0,
            pricePerUnit = 3000.0,
            totalValue = 3000.0,
            timestamp = System.currentTimeMillis()
        )

        assertEquals("SELL", transaction.transactionType)
        assertEquals("ethereum", transaction.assetId)
        assertEquals(1.0, transaction.amount, 0.001)
    }

    @Test
    fun `transaction entity supports stock transactions`() {
        // Test stock transaction
        val transaction = TransactionEntity(
            id = 3,
            assetId = "AAPL",
            assetType = "STOCK",
            assetName = "Apple Inc.",
            assetSymbol = "AAPL",
            transactionType = "BUY",
            amount = 10.0,
            pricePerUnit = 150.0,
            totalValue = 1500.0,
            timestamp = System.currentTimeMillis()
        )

        assertEquals("STOCK", transaction.assetType)
        assertEquals("AAPL", transaction.assetSymbol)
    }

    @Test
    fun `transaction timestamps are stored correctly`() {
        val currentTime = System.currentTimeMillis()
        val transaction = TransactionEntity(
            id = 1,
            assetId = "bitcoin",
            assetType = "CRYPTO",
            assetName = "Bitcoin",
            assetSymbol = "BTC",
            transactionType = "BUY",
            amount = 0.1,
            pricePerUnit = 45000.0,
            totalValue = 4500.0,
            timestamp = currentTime
        )

        assertEquals(currentTime, transaction.timestamp)
        assertTrue("Timestamp should be positive", transaction.timestamp > 0)
    }
}

