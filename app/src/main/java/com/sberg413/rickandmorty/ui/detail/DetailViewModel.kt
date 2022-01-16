package com.sberg413.rickandmorty.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(characterRepository: CharacterRepository): ViewModel() {

    val characterData: LiveData<Character>
        get() = _characterData

    val locationData: LiveData<Location>
        get() = _locationData

    private val _charId = MutableLiveData<Int>()

    private val _characterData = _charId.switchMap { id ->
        characterRepository.getCharacterDetailLiveData(id)
    }

    private val _locationData = characterData.switchMap { character ->
        Log.d(TAG, "character = $character")
        val locationId = character.location.url.replace(Regex(".*/"), "")
        characterRepository.getLocationLiveData(locationId)
    }

    fun initCharacterId(id: Int) {
        _charId.value = id
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}