package com.sberg413.rickandmorty.ui.models

data class Character(
    val id: Int,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val name: String,
    val url: String,
    val created: String
)