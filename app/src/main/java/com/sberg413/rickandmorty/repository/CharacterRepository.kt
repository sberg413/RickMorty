package com.sberg413.rickandmorty.repository

import androidx.lifecycle.MutableLiveData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.models.Location

interface CharacterRepository {

    fun getCharacterListLiveData(name: String) : MutableLiveData<CharacterList>
    fun getCharacterDetailLiveData(id: String) : MutableLiveData<Character>
    fun getLocationLiveData(id: String) : MutableLiveData<Location>
}