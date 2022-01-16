package com.sberg413.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sberg413.rickandmorty.api.ApiService
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apiService: ApiService) : CharacterRepository {

    override fun getCharacterList(name: String?) : Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig( pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { CharacterPagingSource(apiService, name) } )
            .flow
    }

    override fun getCharacterDetailLiveData(id: Int) : MutableLiveData<Character> {

        val mutableLiveData = MutableLiveData<Character>()

        apiService.getCharacterDetail(id).enqueue(object :
            Callback<Character> {
            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }

            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ) {
                val character = response.body()
                Log.d(TAG, character.toString())
                character?.let { mutableLiveData.value = it }
            }

        })

        return mutableLiveData
    }

    override fun getLocationLiveData(id: String) : MutableLiveData<Location> {

        val mutableLiveData = MutableLiveData<Location>()

        apiService.getLocation(id).enqueue(object :
            Callback<Location> {
            override fun onFailure(call: Call<Location>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }

            override fun onResponse(
                call: Call<Location>,
                response: Response<Location>
            ) {
                val location = response.body()
                Log.d(TAG, location.toString())
                location?.let { mutableLiveData.value = it }
            }

        })

        return mutableLiveData
    }

    companion object {
        private const val TAG = "CharacterRepositoryImpl"
    }
}