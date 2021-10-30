package com.sberg413.rickandmorty.api

import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    fun getCharacterList(@Query("name") name: String): Call<CharacterList>

    @GET("character/{id}")
    fun getCharacterDetail(@Path("id")  id: String): Call<Character>

    @GET("location/{id}")
    fun getLocation(@Path("id")  id: String): Call<Location>

}