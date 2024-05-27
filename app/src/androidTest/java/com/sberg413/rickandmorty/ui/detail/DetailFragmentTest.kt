package com.sberg413.rickandmorty.ui.detail


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sberg413.rickandmorty.di.RepositoryModule
import com.sberg413.rickandmorty.launchFragmentInHiltContainer
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.repository.TestCharacterRepositoryImpl
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
//@UninstallModules(RepositoryModule::class)
class DetailFragmentTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

//    @BindValue
//    @JvmField
//    val repository: CharacterRepository = TestCharacterRepositoryImpl()
    @Inject
    lateinit var repository: CharacterRepository

    @Before
    public override fun setUp() {
        hiltRule.inject()
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testDetailDisplayed() = runTest {
        (repository as TestCharacterRepositoryImpl).location = TEST_LOCATION

        val fragmentArgs = Bundle().apply {
            putParcelable(DetailViewModel.KEY_CHARACTER, TEST_CHARACTER)
        }

        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs) {
            // Check if the action bar title is set correctly
            val activity = requireActivity() as AppCompatActivity
            assertEquals("Beth Smith", activity.supportActionBar?.title)
        }

        // Compose assertions
        composeTestRule.onNodeWithTag("CharacterName").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Species").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Location").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Dimension").assertIsDisplayed()
    }

    companion object {

        private val TEST_CHARACTER = Character(
            4,
            "Alive",
            "Human",
            "",
            "Female",
            "20",
            "20",
            "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            "Beth Smith"
        )

        private val TEST_LOCATION = Location(
            id = 20,
            name = "Earth (Replacement Dimension)",
            type = "Planet",
            dimension = "Replacement Dimension",
            residents = null,
            url = "https://rickandmortyapi.com/api/location/20",
            created = "2017-11-18T19:33:01.173Z"
        )
    }
}