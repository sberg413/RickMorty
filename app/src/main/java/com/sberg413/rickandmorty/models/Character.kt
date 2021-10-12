package com.sberg413.rickandmorty.models

import androidx.lifecycle.MutableLiveData

data class Character(
    val id: Int,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: LocationShort,
    var locationDetails: MutableLiveData<Location>?,
    val image: String,
    val name: String,
    val url: String,
    val created: String
)