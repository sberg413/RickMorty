package com.sberg413.rickandmorty.utils

import androidx.recyclerview.widget.DiffUtil
import com.sberg413.rickandmorty.models.Character

object CharacterComparator : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}