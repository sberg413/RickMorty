package com.sberg413.rickandmorty.api

import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.models.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // TODO: pass in param: @Query(value="latlng", encoded=true) String latlng

    @GET("character")
    fun getCharacterList(): Call<CharacterList>

    @GET("character/{id}")
    fun getCharacterDetail(@Path("id")  id: String): Call<Character>

}