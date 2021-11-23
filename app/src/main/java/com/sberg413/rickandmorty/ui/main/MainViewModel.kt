package com.sberg413.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val listData: LiveData<CharacterList> get() = _listData
    private var _listData = MutableLiveData<CharacterList>()

    val navigateToDetails : LiveData<Event<Character>> get() = _navigateToDetails
    private var _navigateToDetails = MutableLiveData<Event<Character>>()

    init{
        updateCharacterList(null)
    }

    fun userClickOnCharacter(character: Character) {
        Log.d("MainViewModel", "clicked id = $character")
        _navigateToDetails.value = Event(character)  // Trigger the event by setting a new Event as a new value
    }

    fun updateCharacterList(name: String?) {
        viewModelScope.launch {
            _isLoading.value = true
             val listDataCall = viewModelScope.async(Dispatchers.IO) {
                 delay(1200) // Short delay to show the progress bar
                 characterRepository.getCharacterList(name)
             }
            val result = listDataCall.await()
            if (result.isSuccess) {
                _listData.value = result.getOrNull()
            } else {
                // TODO: Handle error
            }
            _isLoading.value = false
        }
    }
}