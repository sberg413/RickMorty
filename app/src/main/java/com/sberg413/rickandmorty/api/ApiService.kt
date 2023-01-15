package com.sberg413.rickandmorty.api

import com.sberg413.rickandmorty.api.dto.CharacterListApi
import com.sberg413.rickandmorty.models.Location
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacterList(
        @Query("page") page: Int,
        @Query("name") name: String?,
        @Query("status") status: String?
    ) : CharacterListApi

//    @GET("character/{id}")
//    fun getCharacterDetail(@Path("id")  id: Int): Call<Character>

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: String): Location

}