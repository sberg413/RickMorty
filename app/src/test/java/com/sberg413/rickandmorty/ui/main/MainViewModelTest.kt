package com.sberg413.rickandmorty.ui.main

import androidx.paging.PagingData
import com.sberg413.rickandmorty.MainCoroutineRule
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.util.collectDataForTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val coroutineRule = MainCoroutineRule(testDispatcher)

    @Mock
    lateinit var characterRepository: CharacterRepository

    @Mock
    lateinit var mockCharacter: Character

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun listDataInitialResponse() = runTest {

        val mockCharacterList = listOf(mockCharacter)
        val mockPagingData = PagingData.from(mockCharacterList)
        val dataResponse = flow { emit(mockPagingData) }
        `when`(characterRepository.getCharacterList(any(), any())).thenReturn(
            dataResponse
        )

        val viewModel = MainViewModel(characterRepository)
        val values = mutableListOf<PagingData<Character>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.listData.toList(values)
        }

        assertEquals(mockCharacterList, values[1].collectDataForTest(testDispatcher))
        verify(characterRepository, times(1)).getCharacterList(null,null)

        collectJob.cancel()
    }

    @Test
    fun listDataAfterSetSearchFilter() = runTest {

        val mockCharacterList = listOf(mockCharacter)
        val mockPagingData = PagingData.from(mockCharacterList)
        val dataResponse = flow { emit(mockPagingData) }
        `when`(characterRepository.getCharacterList(any(), any())).thenReturn(
            dataResponse
        )

        val viewModel = MainViewModel(characterRepository)
        val values = mutableListOf<PagingData<Character>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.listData.toList(values)
        }
        viewModel.setSearchFilter(SEARCH_MORTY)

        assertEquals(mockCharacterList, values[2].collectDataForTest(testDispatcher))
        verify(characterRepository, times(1)).getCharacterList(SEARCH_MORTY,null)

        collectJob.cancel()
    }

    @Test
    fun listDataAfterSetStatusFilter() = runTest {

        val mockCharacterList = listOf(mockCharacter)
        val mockPagingData = PagingData.from(mockCharacterList)
        val dataResponse = flow { emit(mockPagingData) }
        `when`(characterRepository.getCharacterList(any(), any())).thenReturn(
            dataResponse
        )

        val viewModel = MainViewModel(characterRepository)
        val values = mutableListOf<PagingData<Character>>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.listData.toList(values)
        }
        viewModel.setStatusFilter(STATUS_ALIVE)

        assertEquals(mockCharacterList, values[2].collectDataForTest(testDispatcher))
        verify(characterRepository, times(1)).getCharacterList(null, STATUS_ALIVE)

        collectJob.cancel()
    }

    companion object {
        const val SEARCH_MORTY = "morty"
        const val STATUS_ALIVE = "alive"
    }
}