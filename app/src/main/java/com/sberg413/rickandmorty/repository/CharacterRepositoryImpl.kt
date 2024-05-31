package com.sberg413.rickandmorty.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sberg413.rickandmorty.api.ApiService
import com.sberg413.rickandmorty.api.dto.CharacterListApi
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apiService: ApiService, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) :
    CharacterRepository {

    //private val regex = ".*/(\\d+)$".toRegex()

    override fun getCharacterList(search: String?, status: String?) : Flow<PagingData<Character>> {
        Log.d(TAG,"getCharacterList() name= $search | status= $status ")
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(apiService, search, status) }
        )
            .flow
            .map { pagingData ->
               pagingData.map {
                   it.toCharacter()
               }
            }
            .flowOn(dispatcher)
    }

    override suspend fun getLocation(id: String): Location = withContext(Dispatchers.IO){
        // emit(Resource.loading(data = null))
        try {
            apiService.getLocation(id)
        } catch (exception: Exception){
            Log.e(TAG,"Error retrieving location! ", exception)
            // emit(Resource.error(data=null,message = exception.message?:"Error occured"))
            throw (exception)
        }
    }

    private fun CharacterListApi.Result.toCharacter(): Character {
        return Character(
            id,
            status,
            species,
            type,
            gender,
            origin.url.split("/").last(),
            location.url.split("/").last(),
            image,
            name)
    }

    companion object {
        private const val TAG = "CharacterRepositoryImpl"
        const val NETWORK_PAGE_SIZE = 20
    }
}