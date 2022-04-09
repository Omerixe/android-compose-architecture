package ch.omerixe.androidarchitecture

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @Before
    fun setUpNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            AppNavigation(navController = navController, mainViewModel = MainViewModel())
        }

    }

    @Test
    fun testLogin() {
        composeTestRule
            .onNodeWithContentDescription("Login Screen")
            .assertIsDisplayed()
    }

    @Test
    fun testHomeWhenLoggedIn() {
        composeTestRule
            .onNodeWithText("Click me!")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Home Screen")
            .assertIsDisplayed()
    }

}