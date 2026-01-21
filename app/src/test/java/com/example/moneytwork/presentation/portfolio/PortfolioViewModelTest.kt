package com.example.moneytwork.presentation.portfolio

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for Portfolio State
 * Tests the PortfolioState data class
 */
class PortfolioStateTest {

    @Test
    fun `PortfolioState initializes with default values`() {
        // Test that PortfolioState can be created with defaults
        val state = PortfolioState()

        assertNotNull("State should not be null", state)
        assertFalse("Initial loading should be false", state.isLoading)
        assertEquals("Initial total investment should be 0", 0.0, state.totalInvestment, 0.01)
        assertEquals("Initial current value should be 0", 0.0, state.currentValue, 0.01)
        assertEquals("Initial profit/loss should be 0", 0.0, state.profitLoss, 0.01)
    }

    @Test
    fun `PortfolioState stores profit correctly`() {
        // Test that state object holds profit values correctly
        val state = PortfolioState(
            isLoading = false,
            totalInvestment = 10000.0,
            currentValue = 12000.0,
            profitLoss = 2000.0,
            profitLossPercentage = 20.0
        )

        assertEquals(10000.0, state.totalInvestment, 0.01)
        assertEquals(12000.0, state.currentValue, 0.01)
        assertEquals(2000.0, state.profitLoss, 0.01)
        assertEquals(20.0, state.profitLossPercentage, 0.01)
        assertFalse(state.isLoading)
    }

    @Test
    fun `PortfolioState stores loss correctly`() {
        // Test loss scenario
        val state = PortfolioState(
            isLoading = false,
            totalInvestment = 10000.0,
            currentValue = 8000.0,
            profitLoss = -2000.0,
            profitLossPercentage = -20.0
        )

        assertTrue("Profit/loss should be negative", state.profitLoss < 0)
        assertTrue("Percentage should be negative", state.profitLossPercentage < 0)
        assertEquals(-2000.0, state.profitLoss, 0.01)
    }
}

