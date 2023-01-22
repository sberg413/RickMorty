package com.sberg413.rickandmorty.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.paging.map
import com.sberg413.rickandmorty.MainCoroutineRule
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.util.collectDataForTest

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val coroutineRule = MainCoroutineRule(testDispatcher)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var characterRepository: CharacterRepository

    @Mock
    lateinit var mockCharacter: Character

    @Mock
    lateinit var mockPagingData: PagingData<Character>


    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getData() = runTest {

        val mockCharacterList = listOf(mockCharacter)
        val mockPagingData = PagingData.from(mockCharacterList)
        val dataResponse = flow {
            emit(mockPagingData)
        }
        `when`(characterRepository.getCharacterList(any(), any())).thenReturn(
            dataResponse
        )

        val viewModel = MainViewModel(characterRepository)

        val first = viewModel.listData.take(1).first()
        assertEquals(PagingData.empty<Character>(), first)
       verifyNoInteractions(characterRepository)

        advanceUntilIdle()

        viewModel.setSearchFilter("morty")
        advanceUntilIdle()
        verify(characterRepository).getCharacterList("morty",null)
        val next = viewModel.listData.take(1).first()
        assertEquals(mockCharacterList, next.collectDataForTest(testDispatcher))
    }
}