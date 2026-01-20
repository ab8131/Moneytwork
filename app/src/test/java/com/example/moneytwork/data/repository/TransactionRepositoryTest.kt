package com.example.moneytwork.data.repository

import com.example.moneytwork.data.local.entity.TransactionEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionCalculationTest {

    @Test
    fun `calculate total value from BUY transaction`() {
        // Given
        val amount = 0.5
        val pricePerUnit = 50000.0

        // When
        val totalValue = amount * pricePerUnit

        // Then
        assertEquals(25000.0, totalValue, 0.01)
    }

    @Test
    fun `calculate profit from multiple transactions`() {
        // Given: Bought 0.01 BTC at $40k and 0.01 BTC at $50k
        val transaction1Value = 400.0
        val transaction2Value = 500.0
        val totalInvested = transaction1Value + transaction2Value // 900

        // Current value: 0.02 BTC at $60k
        val currentPrice = 60000.0
        val totalAmount = 0.02
        val currentValue = totalAmount * currentPrice // 1200

        // When
        val profit = currentValue - totalInvested // 300
        val profitPercentage = (profit / totalInvested) * 100 // 33.33%

        // Then
        assertEquals(300.0, profit, 0.01)
        assertEquals(33.33, profitPercentage, 0.1)
    }

    @Test
    fun `calculate remaining holdings after sell`() {
        // Given: Bought 0.1 BTC, sold 0.05 BTC
        val initialAmount = 0.1
        val soldAmount = 0.05

        // When
        val remainingAmount = initialAmount - soldAmount

        // Then
        assertEquals(0.05, remainingAmount, 0.001)
    }

    @Test
    fun `transaction entity should store correct values`() {
        // Given
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

        // Then
        assertEquals("bitcoin", transaction.assetId)
        assertEquals("CRYPTO", transaction.assetType)
        assertEquals(0.5, transaction.amount, 0.001)
        assertEquals(50000.0, transaction.pricePerUnit, 0.01)
        assertEquals(25000.0, transaction.totalValue, 0.01)
    }

    @Test
    fun `net investment after buy and sell`() {
        // Given: Buy for 4000, Sell for 2500
        val buyValue = 4000.0
        val sellValue = 2500.0

        // When
        val netInvestment = buyValue - sellValue

        // Then
        assertEquals(1500.0, netInvestment, 0.01)
    }
}

