package com.sberg413.rickandmorty.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.repository.CharacterRepository

import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var characterRepository: CharacterRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(characterRepository)
    }

    @Test
    fun getData() {
        val dataResponse = MutableLiveData<CharacterList>()
        `when`(characterRepository.getCharacterListLiveData("")).then {
            dataResponse
        }
        val viewModel = MainViewModel(characterRepository)

        assertNotNull(viewModel.listData)
        assertEquals(viewModel.listData.value,  null)
    }
}