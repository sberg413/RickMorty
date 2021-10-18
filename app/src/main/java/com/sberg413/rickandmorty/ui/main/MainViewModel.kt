package com.sberg413.rickandmorty.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(characterRepository: CharacterRepository): ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var _listData = MutableLiveData<CharacterList>()

    init{
        _listData = characterRepository.getCharacterListLiveData()
    }

    fun getData() : MutableLiveData<CharacterList> {
        return _listData
    }
}