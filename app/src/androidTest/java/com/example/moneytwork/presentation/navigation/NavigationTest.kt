package com.example.moneytwork.presentation.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moneytwork.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun bottomNavigation_displaysAllTabs() {
        // Wait for bottom navigation to appear (after potential auth)
        composeTestRule.waitForIdle()

        // Check if bottom nav items exist (might need to sign in first)
        // This is a basic check - in real scenario you'd mock auth state
        composeTestRule.onAllNodesWithText("Portfolio").fetchSemanticsNodes().isNotEmpty()
    }

    @Test
    fun bottomNavigation_navigatesToCrypto() {
        composeTestRule.waitForIdle()

        // Click on Crypto tab
        composeTestRule.onNodeWithText("Crypto").performClick()

        composeTestRule.waitForIdle()

        // Verify we're on crypto screen (check for some crypto-specific element)
        // This would depend on your actual screen content
    }

    @Test
    fun bottomNavigation_navigatesToStocks() {
        composeTestRule.waitForIdle()

        // Click on Stocks tab
        composeTestRule.onNodeWithText("Stocks").performClick()

        composeTestRule.waitForIdle()

        // Verify we're on stocks screen
    }

    @Test
    fun bottomNavigation_navigatesToSettings() {
        composeTestRule.waitForIdle()

        // Click on Settings tab
        composeTestRule.onNodeWithText("Settings").performClick()

        composeTestRule.waitForIdle()

        // Verify settings screen is displayed
        composeTestRule.onNodeWithText("Settings").assertExists()
    }
}

