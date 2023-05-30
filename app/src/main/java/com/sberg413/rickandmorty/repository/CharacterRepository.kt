package com.sberg413.rickandmorty.repository

import androidx.paging.PagingData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {


    fun getCharacterList( search: String?, status: String?): Flow<PagingData<Character>>
    suspend fun getLocation(id: String) : Location
}