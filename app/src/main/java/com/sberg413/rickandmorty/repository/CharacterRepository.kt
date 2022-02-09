package com.sberg413.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacterList( name: String?): Flow<PagingData<Character>>
    fun getLocation(id: String) : LiveData<Location>
}