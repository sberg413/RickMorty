package com.sberg413.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sberg413.rickandmorty.api.ApiService
import com.sberg413.rickandmorty.models.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apiService: ApiService) : CharacterRepository {

    override fun getCharacterList(name: String?, status: String?) : Flow<PagingData<Character>> {
        Log.d(TAG,"getCharacterList() name= $name | status= $status ")
        return Pager(
            config = PagingConfig( pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { CharacterPagingSource(apiService, name, status) } )
            .flow
    }

    override fun getLocation(id: String)  = liveData(Dispatchers.IO) {
        // emit(Resource.loading(data = null))
        try {
            emit(apiService.getLocation(id))
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