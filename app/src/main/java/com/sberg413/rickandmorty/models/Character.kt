package com.sberg413.rickandmorty.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originId: String,
    val locationId: String,
    val image: String,
    val name: String,
    // val url: String,
    // val created: String
) : Parcelable