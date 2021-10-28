package com.sberg413.rickandmorty.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    private var listData = MutableLiveData<CharacterList>()

    init{
        listData = characterRepository.getCharacterListLiveData("")
    }

    fun getData() : MutableLiveData<CharacterList> {
        return listData
    }

    fun search(name: String) : MutableLiveData<CharacterList> {
        listData = characterRepository.getCharacterListLiveData(name)
        return listData
    }
}