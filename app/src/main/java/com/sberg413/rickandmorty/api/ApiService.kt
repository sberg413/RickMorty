package com.sberg413.rickandmorty.api

import com.sberg413.rickandmorty.models.CharacterList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    // TODO: pass in param: @Query(value="latlng", encoded=true) String latlng

    @GET("character")
    fun getCharacterList(): Call<CharacterList>

}