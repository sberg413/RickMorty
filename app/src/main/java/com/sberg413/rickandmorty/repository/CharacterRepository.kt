package com.sberg413.rickandmorty.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sberg413.rickandmorty.api.ApiClient

import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.ui.models.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterRepository {

    fun getCharacterListLiveData(context: Context) : MutableLiveData<CharacterList> {

        val mutableLiveData = MutableLiveData<CharacterList>()

        // context.showProgressBar()

        ApiClient.apiService.getCharacterList().enqueue(object :
            Callback<CharacterList> {
            override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                // hideProgressBar()
                Log.e("error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CharacterList>,
                response: Response<CharacterList>
            ) {
                // hideProgressBar()
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
                Log.e("error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ) {
                // hideProgressBar()
                val character = response.body()
                Log.d(TAG, character.toString())
                character?.let { mutableLiveData.value = it }
            }

        })

        return mutableLiveData
    }

    companion object {
        private const val TAG = "CharacterRepository"
    }
}