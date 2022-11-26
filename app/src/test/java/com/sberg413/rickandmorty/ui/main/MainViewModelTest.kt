package com.sberg413.rickandmorty.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.sberg413.rickandmorty.MainCoroutineRule
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.repository.CharacterRepository

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
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

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var characterRepository: CharacterRepository

    @Mock
    lateinit var mockCharacter: Character

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(characterRepository)
    }

    @Test

    fun getData() = runTest {
        val dataResponse = flow {
            emit(PagingData.from(listOf(mockCharacter)))
        }
        `when`(characterRepository.getCharacterList(any(), any())).thenReturn(
            dataResponse
        )

        viewModel.setSearchFilter("morty")
        viewModel.setSatusFilter("Dead")

       // given(characterRepository.getCharacterList(any(), any())).willReturn(dataResponse)

        assertEquals(dataResponse, viewModel.listData)
       //  assertEquals(viewModel.listData.value,  null)
    }
}