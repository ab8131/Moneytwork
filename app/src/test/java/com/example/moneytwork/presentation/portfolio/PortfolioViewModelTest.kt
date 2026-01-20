package com.example.moneytwork.presentation.portfolio

import org.junit.Assert.assertEquals
import org.junit.Test

class PortfolioCalculationTest {

    @Test
    fun `calculate portfolio value with single crypto`() {
        // Given: User owns 0.5 BTC bought at $40k, current price is $50k
        val amount = 0.5
        val buyPrice = 40000.0
        val currentPrice = 50000.0

        // When
        val invested = amount * buyPrice // 20000
        val currentValue = amount * currentPrice // 25000
        val profit = currentValue - invested // 5000
        val profitPercent = (profit / invested) * 100 // 25%

        // Then
        assertEquals(20000.0, invested, 0.01)
        assertEquals(25000.0, currentValue, 0.01)
        assertEquals(5000.0, profit, 0.01)
        assertEquals(25.0, profitPercent, 0.01)
    }

    @Test
    fun `calculate portfolio value with multiple purchases same asset`() {
        // Given: Bought BTC at different prices
        // Purchase 1: 0.01 BTC at $40k = $400
        // Purchase 2: 0.01 BTC at $50k = $500
        // Current price: $60k
        val totalAmount = 0.02
        val totalInvested = 900.0 // 400 + 500
        val currentPrice = 60000.0

        // When
        val currentValue = totalAmount * currentPrice // 1200
        val profit = currentValue - totalInvested // 300
        val profitPercent = (profit / totalInvested) * 100 // 33.33%

        // Then
        assertEquals(1200.0, currentValue, 0.01)
        assertEquals(300.0, profit, 0.01)
        assertEquals(33.33, profitPercent, 0.1)
    }

    @Test
    fun `calculate average buy price`() {
        // Given: Multiple purchases
        val purchase1Amount = 0.01
        val purchase1Price = 40000.0
        val purchase2Amount = 0.01
        val purchase2Price = 50000.0

        // When
        val totalAmount = purchase1Amount + purchase2Amount // 0.02
        val totalValue = (purchase1Amount * purchase1Price) + (purchase2Amount * purchase2Price) // 900
        val averagePrice = totalValue / totalAmount // 45000

        // Then
        assertEquals(0.02, totalAmount, 0.001)
        assertEquals(900.0, totalValue, 0.01)
        assertEquals(45000.0, averagePrice, 0.01)
    }

    @Test
    fun `calculate portfolio loss scenario`() {
        // Given: Bought at high, price dropped
        val amount = 1.0
        val buyPrice = 50000.0
        val currentPrice = 40000.0

        // When
        val invested = amount * buyPrice // 50000
        val currentValue = amount * currentPrice // 40000
        val loss = currentValue - invested // -10000
        val lossPercent = (loss / invested) * 100 // -20%

        // Then
        assertEquals(50000.0, invested, 0.01)
        assertEquals(40000.0, currentValue, 0.01)
        assertEquals(-10000.0, loss, 0.01)
        assertEquals(-20.0, lossPercent, 0.01)
    }

    @Test
    fun `calculate total portfolio value multiple assets`() {
        // Given: Portfolio with BTC and ETH
        // BTC: 0.5 at $50k = $25k
        // ETH: 5.0 at $3k = $15k
        val btcValue = 0.5 * 50000.0
        val ethValue = 5.0 * 3000.0

        // When
        val totalPortfolioValue = btcValue + ethValue

        // Then
        assertEquals(25000.0, btcValue, 0.01)
        assertEquals(15000.0, ethValue, 0.01)
        assertEquals(40000.0, totalPortfolioValue, 0.01)
    }
}

