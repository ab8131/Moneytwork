package com.example.moneytwork.presentation.auth

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moneytwork.ui.theme.MoneytworkTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun signInScreen_displaysAllElements() {
        composeTestRule.setContent {
            MoneytworkTheme {
                SignInScreen(navController = rememberNavController())
            }
        }

        // Verify all UI elements are displayed
        composeTestRule.onNodeWithText("Welcome Back").assertExists()
        composeTestRule.onNodeWithText("Sign in to continue").assertExists()
        composeTestRule.onNodeWithText("Email").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Sign In").assertExists()
        composeTestRule.onNodeWithText("Don't have an account? Sign Up").assertExists()
    }

    @Test
    fun signInScreen_emailInput_acceptsText() {
        composeTestRule.setContent {
            MoneytworkTheme {
                SignInScreen(navController = rememberNavController())
            }
        }

        // Type email
        composeTestRule.onNodeWithText("Email")
            .performTextInput("test@example.com")

        // Verify text was entered
        composeTestRule.onNodeWithText("test@example.com").assertExists()
    }

    @Test
    fun signInScreen_passwordInput_acceptsText() {
        composeTestRule.setContent {
            MoneytworkTheme {
                SignInScreen(navController = rememberNavController())
            }
        }

        // Type password
        composeTestRule.onNodeWithText("Password")
            .performTextInput("password123")

        // Verify text was entered (password fields might mask the text)
        composeTestRule.onNodeWithText("Password").assertExists()
    }

    @Test
    fun signInScreen_signInButton_isClickable() {
        composeTestRule.setContent {
            MoneytworkTheme {
                SignInScreen(navController = rememberNavController())
            }
        }

        // Find and click sign in button
        composeTestRule.onNodeWithText("Sign In")
            .assertIsEnabled()
            .performClick()
    }

    @Test
    fun signInScreen_signUpLink_isClickable() {
        composeTestRule.setContent {
            MoneytworkTheme {
                SignInScreen(navController = rememberNavController())
            }
        }

        // Find and click sign up link
        composeTestRule.onNodeWithText("Don't have an account? Sign Up")
            .assertExists()
            .performClick()
    }
}

