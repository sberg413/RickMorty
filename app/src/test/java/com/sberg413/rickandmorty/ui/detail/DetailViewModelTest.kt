package com.sberg413.rickandmorty.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.sberg413.rickandmorty.MainCoroutineRule
import com.sberg413.rickandmorty.TestData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.internal.stubbing.answers.AnswersWithDelay
import org.mockito.internal.stubbing.answers.Returns

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val coroutineRule = MainCoroutineRule(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private var characterRepository: CharacterRepository = mock()


    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        val character = TestData.TEST_CHARACTER
        savedStateHandle = SavedStateHandle(mapOf(DetailViewModel.KEY_CHARACTER to character))
    }

    @Test
    fun testLoadingState() = runTest {
        // Given
        `when`(characterRepository.getLocation(anyString())).thenAnswer(
            AnswersWithDelay(1000, Returns(TestData.TEST_LOCATION)))

        // When
        viewModel = DetailViewModel(characterRepository, savedStateHandle)

        // Then
        assertEquals(CharacterDetailUiState.Loading, viewModel.uiState.first())
    }

    @Test
    fun testSuccessState() = runTest {
        // Given
        val location = TestData.TEST_LOCATION
        val character = savedStateHandle.get<Character>(DetailViewModel.KEY_CHARACTER)!!
        `when`(characterRepository.getLocation(character.locationId!!)).thenReturn(location)

        // When
        viewModel = DetailViewModel(characterRepository, savedStateHandle)
        advanceUntilIdle() // Wait for all coroutines to finish

        // Then
        assertEquals(CharacterDetailUiState.Success(character, location), viewModel.uiState.first())
    }
}
