package com.sberg413.rickandmorty.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import com.sberg413.rickandmorty.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class DetailViewModel(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characterData: StateFlow<Character?> =
        savedStateHandle.getStateFlow<Character?>("character", null)

    val locationData: StateFlow<Location?> = characterData.map { character ->
        if (character == null) return@map null
        Log.d(TAG, "character = $character")
        val locationId = character.location.url.replace(Regex(".*/"), "")
        characterRepository.getLocation(locationId)
    }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    companion object {
        private const val TAG = "DetailViewModel"
    }
}