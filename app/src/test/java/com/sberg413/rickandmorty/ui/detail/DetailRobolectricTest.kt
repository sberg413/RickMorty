package com.sberg413.rickandmorty.ui.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sberg413.rickandmorty.TestData
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog


@RunWith(AndroidJUnit4::class)
class DetailRobolectricTest : TestCase() {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    public override fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testDetailDisplayed() {

        composeTestRule.setContent {
            CharacterDetailContent(
                characterData = TestData.TEST_CHARACTER,
                locationData = TestData.TEST_LOCATION
            )
        }

        // Compose assertions
        composeTestRule.onNodeWithTag("CharacterName").assertExists()
        composeTestRule.onNodeWithTag("Species").assertExists()
        composeTestRule.onNodeWithTag("Location").assertExists()
        composeTestRule.onNodeWithTag("Dimension").assertExists()
    }

}