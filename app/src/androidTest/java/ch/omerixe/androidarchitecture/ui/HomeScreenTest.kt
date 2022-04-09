package ch.omerixe.androidarchitecture.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ch.omerixe.androidarchitecture.ui.theme.AndroidArchitectrureTheme
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private var detailClicked = false
    private var logoutClicked = false
    private var menuClicked = false

    @Before
    fun setUp() {
        detailClicked = false
        logoutClicked = false
        menuClicked = false

        composeTestRule.setContent {
            AndroidArchitectrureTheme {
                HomeScreen(
                    onDetailClick = { detailClicked = true },
                    onLogoutClick = { logoutClicked = true },
                    onMenuClicked = { menuClicked = true }
                )
            }
        }
    }

    @Test
    fun testDetailClick() {
        composeTestRule.onNodeWithText("Click me (Detail)!").performClick()
        assertTrue(detailClicked)
    }

    @Test
    fun testLogoutClick() {
        composeTestRule.onNodeWithText("Log Out!").performClick()
        assertTrue(logoutClicked)
    }

    @Test
    fun testMenuClick() {
        composeTestRule.onNodeWithContentDescription("Open Menu").performClick()
        assertTrue(menuClicked)
    }
}