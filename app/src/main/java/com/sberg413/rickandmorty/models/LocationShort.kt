package com.sberg413.rickandmorty.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationShort(
    val name: String,
    val url: String
) : Parcelable
