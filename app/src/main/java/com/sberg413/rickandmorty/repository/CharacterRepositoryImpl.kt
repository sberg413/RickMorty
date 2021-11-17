package com.sberg413.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sberg413.rickandmorty.api.ApiService
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.models.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val apiService: ApiService) : CharacterRepository {

    override suspend fun getCharacterList(name: String?) : Result<CharacterList> {
        return try {
            val response = apiService.getCharacterList(1, name).execute()
            if (response.isSuccessful) {
                val characterList = response.body() ?: throw IOException("Character list is null!")
                Log.d(TAG, characterList.toString())
                Result.success(characterList)
            } else {
                throw IOException(response.message())
            }
        } catch (e: IOException) {
            Log.e(TAG, "getCharacterList error: ${e.message}")
            Result.failure(e)
        }
    }

    override fun getCharacterDetailLiveData(id: String) : MutableLiveData<Character> {

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