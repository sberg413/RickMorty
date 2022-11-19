package com.sberg413.rickandmorty.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sberg413.rickandmorty.api.ApiService
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(private val apiService: ApiService) : CharacterRepository {

    override fun getCharacterList(search: String?, status: String?) : Flow<PagingData<Character>> {
        Log.d(TAG,"getCharacterList() name= $search | status= $status ")
        return Pager(
            config = PagingConfig( pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { CharacterPagingSource(apiService, search, status) } )
            .flow
    }

    override suspend fun getLocation(id: String): Location {
        // emit(Resource.loading(data = null))
        try {
           return apiService.getLocation(id)
        } catch (exception: Exception){
            Log.e(TAG,"Error retrieving location! ", exception)
            // emit(Resource.error(data=null,message = exception.message?:"Error occured"))
            throw (exception)
        }
    }

    companion object {
        private const val TAG = "CharacterRepositoryImpl"
    }
}