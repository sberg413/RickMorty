package com.sberg413.rickandmorty.ui.main

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sberg413.rickandmorty.launchFragmentInHiltContainer
import org.junit.Assert.*

import junit.framework.TestCase

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest : TestCase() {

    @Before
    public override fun setUp() {
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun testMainFragment() {
        // The "fragmentArgs" argument is optional.
        val fragmentArgs = bundleOf()
        val scenario = launchFragmentInHiltContainer<MainFragment>(fragmentArgs)
    }

}