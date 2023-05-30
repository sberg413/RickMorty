package com.sberg413.rickandmorty.api.dto

class CharacterListApi(
    val info: Info,
    val results: List<Result>
) {
    class Info(
        val count: Int,
        val next: String?,
        val pages: Int,
        val prev: String?
    )

    class Result(
        val created: String,
        val episode: List<String>,
        val gender: String,
        val id: Int,
        val image: String,
        val location: Location,
        val name: String,
        val origin: Origin,
        val species: String,
        val status: String,
        val type: String,
        val url: String
    ) {
        class Origin(
            val name: String,
            val url: String
        )

        class Location(
            val name: String,
            val url: String
        )

    }
}
