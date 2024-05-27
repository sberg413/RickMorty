package com.sberg413.rickandmorty.repository

import androidx.paging.PagingData
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestCharacterRepositoryImpl @Inject constructor(): CharacterRepository {

    var location: Location? = null

    override fun getCharacterList(search: String?, status: String?): Flow<PagingData<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocation(id: String): Location {
        return location ?: throw Exception("Location not found in test! Did you set it?")
    }

}