package com.sberg413.rickandmorty.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacterList( name: String?): Flow<PagingData<Character>>
    fun getCharacterDetailLiveData(id: Int) : MutableLiveData<Character>
    fun getLocationLiveData(id: String) : MutableLiveData<Location>
}