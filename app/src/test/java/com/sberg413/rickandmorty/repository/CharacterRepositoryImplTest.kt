package com.sberg413.rickandmorty.repository

import androidx.paging.testing.asSnapshot
import com.sberg413.rickandmorty.MainCoroutineRule
import com.sberg413.rickandmorty.TestData.TEST_LOCATION
import com.sberg413.rickandmorty.TestData.readJsonFile
import com.sberg413.rickandmorty.api.ApiService
import com.sberg413.rickandmorty.api.MockApiService
import com.sberg413.rickandmorty.api.dto.CharacterListApi
import com.sberg413.rickandmorty.util.collectDataForTest
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CharacterRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule(testDispatcher)

    private val mockApiServices: ApiService = mock()

    lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Before
    fun setUp() {
        characterRepositoryImpl = CharacterRepositoryImpl(mockApiServices, testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getCharacterList() = runTest {
        // When
        val jsonString = readJsonFile("characters_response.json")
        val moshiAdapter = Moshi.Builder().build().adapter(CharacterListApi::class.java)
        val characterListApi = moshiAdapter.fromJson(jsonString)
       `when`(mockApiServices.getCharacterList(anyInt(), any(), any())).thenReturn(characterListApi)

        // Then
        val list = characterRepositoryImpl.getCharacterList(null, null).asSnapshot()

        // Verify
        assertEquals(40, list.size)
        assertEquals("Rick Sanchez", list[0].name)
        assertEquals("Male", list[0].gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", list[0].image)
        assertEquals(1, list[0].id)
    }

    @Test
    fun getLocation() = runTest {
        val id = "20"
        `when`(mockApiServices.getLocation(id)).thenReturn(TEST_LOCATION)

        val result = characterRepositoryImpl.getLocation(id)

        assertEquals(TEST_LOCATION, result)
    }
}