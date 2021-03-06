package com.sberg413.rickandmorty.ui.detail

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(characterRepository: CharacterRepository): ViewModel() {

    val characterData: LiveData<Character>
        get() = _characterData

    val locationData: LiveData<Location>
        get() = _locationData

    private val _charId = MutableLiveData<String>()

    private val _characterData = _charId.switchMap { id ->
        characterRepository.getCharacterDetailLiveData(id)
    }

    private val _locationData = characterData.switchMap { character ->
        Log.d(TAG, "character = $character")
        val locationId = character.location.url.replace(Regex(".*/"), "")
        characterRepository.getLocationLiveData(locationId)
    }

    fun initCharacterId(id: String) {
        _charId.value = id
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}