package com.sberg413.rickandmorty.models

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>?,
    val url: String,
    val created: String
) {

    val residentCount: Int
        get() = residents?.size ?: 0

}
