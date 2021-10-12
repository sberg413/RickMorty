package com.sberg413.rickandmorty.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.utils.isInternetAvailable

class MainViewModel(private val context: Context) : ViewModel() {

    private var listData = MutableLiveData<CharacterList>()

    init{
        val characterRepository : CharacterRepository by lazy {
            CharacterRepository()
        }
        if(isInternetAvailable(context)) {
            listData = characterRepository.getCharacterListLiveData(context)
        }
    }

    fun getData() : MutableLiveData<CharacterList> {
        return listData
    }
}