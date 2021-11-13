package com.sberg413.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    val listData: LiveData<CharacterList> get() = _listData
    private var _listData = MutableLiveData<CharacterList>()

    val navigateToDetails : LiveData<Event<Character>> get() = _navigateToDetails
    private val _navigateToDetails = MutableLiveData<Event<Character>>()

    init{
        _listData = characterRepository.getCharacterListLiveData("")
    }

    fun userClickOnCharacter(character: Character) {
        Log.d("MainViewModel", "clicked id = $character")
        _navigateToDetails.value = Event(character)  // Trigger the event by setting a new Event as a new value
    }

    fun search(name: String) : MutableLiveData<CharacterList> {
        _listData = characterRepository.getCharacterListLiveData(name)
        return _listData
    }
}