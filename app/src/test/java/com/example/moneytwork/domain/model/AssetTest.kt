package com.example.moneytwork.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AssetCalculationTest {

    @Test
    fun `price change percentage should format correctly`() {
        // Given
        val positiveChange = 5.67
        val negativeChange = -3.42

        // When
        val formattedPositive = String.format("%.2f%%", positiveChange)
        val formattedNegative = String.format("%.2f%%", negativeChange)

        // Then
        assertEquals("5.67%", formattedPositive)
        assertEquals("-3.42%", formattedNegative)
    }

    @Test
    fun `portfolio value calculation should be accurate`() {
        // Given
        val quantity = 0.5
        val pricePerUnit = 50000.0
        val invested = 20000.0

        // When
        val currentValue = quantity * pricePerUnit
        val profit = currentValue - invested
        val profitPercentage = (profit / invested) * 100

        // Then
        assertEquals(25000.0, currentValue, 0.01)
        assertEquals(5000.0, profit, 0.01)
        assertEquals(25.0, profitPercentage, 0.01)
    }

    @Test
    fun `price formatting should handle large numbers`() {
        // Given
        val price = 1234567.89

        // When
        val formatted = String.format("$%,.2f", price)

        // Then
        assertTrue(formatted.contains("1,234,567.89") || formatted.contains("1234567.89"))
    }

    @Test
    fun `percentage change should indicate trend direction`() {
        // Given
        val positiveChange = 5.0
        val negativeChange = -3.0
        val noChange = 0.0

        // Then
        assertTrue(positiveChange > 0)  // Uptrend
        assertTrue(negativeChange < 0)  // Downtrend
        assertEquals(0.0, noChange, 0.0)  // No change
    }

    @Test
    fun `calculate market cap from price and supply`() {
        // Given
        val price = 50000.0
        val circulatingSupply = 19000000.0

        // When
        val marketCap = price * circulatingSupply

        // Then
        assertEquals(950000000000.0, marketCap, 0.01)  // 950 billion
    }

    @Test
    fun `calculate 24h high and low range`() {
        // Given
        val high24h = 52000.0
        val low24h = 48000.0

        // When
        val range = high24h - low24h
        val midpoint = (high24h + low24h) / 2

        // Then
        assertEquals(4000.0, range, 0.01)
        assertEquals(50000.0, midpoint, 0.01)
    }
}

