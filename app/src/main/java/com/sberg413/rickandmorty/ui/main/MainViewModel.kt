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
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    val listData: LiveData<CharacterList>
        get() = _listData

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var _listData = MutableLiveData<CharacterList>()

    init{
        _listData = characterRepository.getCharacterListLiveData("")
    }

    fun search(name: String) : MutableLiveData<CharacterList> {
        _listData = characterRepository.getCharacterListLiveData(name)
        return _listData
    }
}