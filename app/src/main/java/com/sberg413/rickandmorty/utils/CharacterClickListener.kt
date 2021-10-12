package com.sberg413.rickandmorty.utils

import com.sberg413.rickandmorty.ui.models.Character

interface CharacterClickListener {
    fun characterClicked(character: Character)
}