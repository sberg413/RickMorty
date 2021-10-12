package com.sberg413.rickandmorty.models

import com.sberg413.rickandmorty.ui.models.Character
import com.sberg413.rickandmorty.ui.models.Info

data class CharacterList(
    val info: Info,
    val results: List<Character>
)