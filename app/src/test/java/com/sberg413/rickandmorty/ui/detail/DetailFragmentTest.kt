package com.sberg413.rickandmorty.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.launchFragmentInHiltContainer
import com.sberg413.rickandmorty.models.Character
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class DetailFragmentTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    public override fun setUp() {
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testDetailDisplayed() {
        val fragmentArgs = Bundle().apply {
            putParcelable(DetailViewModel.KEY_CHARACTER, TEST_CHARACTER)
        }

        launchFragmentInHiltContainer<DetailFragment>(fragmentArgs){

            assertEquals(TEST_CHARACTER.name, activity?.findViewById<TextView>(R.id.name)?.text)
        }
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
    }
}