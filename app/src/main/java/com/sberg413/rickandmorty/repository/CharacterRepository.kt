package com.sberg413.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sberg413.rickandmorty.api.ApiClient

import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterRepository {

    fun getCharacterListLiveData() : MutableLiveData<CharacterList> {

        val mutableLiveData = MutableLiveData<CharacterList>()

        ApiClient.apiService.getCharacterList().enqueue(object :
            Callback<CharacterList> {
            override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CharacterList>,
                response: Response<CharacterList>
            ) {
                val characterList = response.body()
                Log.d(TAG, characterList.toString())
                characterList?.let { mutableLiveData.value = it }
            }

        })

        return mutableLiveData
    }

    fun getCharacterDetailLiveData(id: String) : MutableLiveData<Character> {

        val mutableLiveData = MutableLiveData<Character>()

        ApiClient.apiService.getCharacterDetail(id).enqueue(object :
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

    fun getLocationLiveData(id: String) : MutableLiveData<Location> {

        val mutableLiveData = MutableLiveData<Location>()

        ApiClient.apiService.getLocation(id).enqueue(object :
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
        private const val TAG = "CharacterRepository"
    }
}