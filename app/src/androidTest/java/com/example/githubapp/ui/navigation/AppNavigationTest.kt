package com.example.githubapp.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.githubapp.ui.navigation.GithubAppNavHost
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppNavigationTest{
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            GithubAppNavHost(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithText("githubapp")
            .assertIsDisplayed()
    }

    @Test
    fun appNavHost_afterSplashWaitTimeout_navigateToWelcome() {
        composeTestRule.waitUntil(3_000, {true})

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals("welcome", route)
    }
}