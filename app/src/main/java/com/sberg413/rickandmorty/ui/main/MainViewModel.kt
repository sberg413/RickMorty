package com.sberg413.rickandmorty.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.repository.CharacterRepository

class MainViewModel : ViewModel() {

    private var listData = MutableLiveData<CharacterList>()

    init{
        val characterRepository : CharacterRepository by lazy {
            CharacterRepository()
        }
        listData = characterRepository.getCharacterListLiveData()
    }

    fun getData() : MutableLiveData<CharacterList> {
        return listData
    }
}