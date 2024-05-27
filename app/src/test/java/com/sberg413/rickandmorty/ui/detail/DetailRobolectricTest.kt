package com.sberg413.rickandmorty.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sberg413.rickandmorty.TestData
import com.sberg413.rickandmorty.di.RepositoryModule
import com.sberg413.rickandmorty.launchFragmentInHiltContainer
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.repository.TestCharacterRepositoryImpl
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)
@Config(application = HiltTestApplication::class)
class DetailRobolectricTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @BindValue
    @JvmField
    val repository: CharacterRepository = TestCharacterRepositoryImpl()

    @Before
    public override fun setUp() {
        hiltRule.inject()
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testDetailDisplayed() {
        (repository as TestCharacterRepositoryImpl).location = TestData.TEST_LOCATION

        val fragmentArgs = Bundle().apply {
            putParcelable(DetailViewModel.KEY_CHARACTER, TestData.TEST_CHARACTER)
        }

        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs){
            // Check if the action bar title is set correctly
            val activity = requireActivity() as AppCompatActivity
            assertEquals("Beth Smith", activity.supportActionBar?.title)
        }

        // Compose assertions
        composeTestRule.onNodeWithTag("CharacterName").assertExists()
        composeTestRule.onNodeWithTag("Species").assertExists()
        composeTestRule.onNodeWithTag("Location").assertExists()
        composeTestRule.onNodeWithTag("Dimension").assertExists()
    }

    companion object {


    }
}