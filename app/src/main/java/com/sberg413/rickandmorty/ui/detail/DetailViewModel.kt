package com.sberg413.rickandmorty.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class CharacterDetailUiState(
    val character: Character? = null,
    val location: Location? = null
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailUiState())
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    //    private val characterData: StateFlow<Character?> =
    companion object {
        private const val TAG = "DetailViewModel"
        const val KEY_CHARACTER = "character"
    }

    init {
        savedStateHandle.get<Character>(KEY_CHARACTER)?.let {
            _uiState.update { currentState ->
                currentState.copy(character = it)
            }
            getLocationFromCharacter(it)
        }

    }

    private fun getLocationFromCharacter(character: Character) {
        Log.d(TAG, "character = $character")
        viewModelScope.launch {
            val locationId = character.locationId // location?.url?.replace(Regex(".*/"), "") ?: ""
            val location = withContext(Dispatchers.IO) {
                characterRepository.getLocation(locationId)
            }
            _uiState.update { currentState ->
                currentState.copy(location = location)
            }
        }
    }
}
