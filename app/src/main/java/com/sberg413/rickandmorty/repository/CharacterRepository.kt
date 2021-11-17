package com.sberg413.rickandmorty.repository

import androidx.lifecycle.MutableLiveData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.models.Location

interface CharacterRepository {

    suspend fun getCharacterList( name: String?): Result<CharacterList?>
    fun getCharacterDetailLiveData(id: String) : MutableLiveData<Character>
    fun getLocationLiveData(id: String) : MutableLiveData<Location>
}