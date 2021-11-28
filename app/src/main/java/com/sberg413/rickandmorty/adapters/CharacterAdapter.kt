package com.sberg413.rickandmorty.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.BR
import com.sberg413.rickandmorty.databinding.CharacterRowBinding
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.ui.main.MainViewModel
import com.sberg413.rickandmorty.utils.CharacterComparator

class CharacterAdapter(private val mainViewModel: MainViewModel) :
    PagingDataAdapter<Character, CharacterAdapter.MyViewHolder>(CharacterComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val characterBinding = CharacterRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "onCreateViewHolder: $viewType")
        return MyViewHolder(characterBinding, mainViewModel)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        (holder as? MyViewHolder)?.bind(getItem(position))
    }

    // Passing in ViewDataBindng instead of CharacterRowBinding is more generic.
    // Would be easier to support multiple types of rows.
    class MyViewHolder(private val binding: ViewDataBinding,
                       private val mainViewModel: MainViewModel)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(character: Character?) {
            Log.d(TAG, "bind: $character")
            if (character != null) {
                binding.root.tag = character.id
                binding.setVariable(BR.character,character)
                binding.setVariable(BR.viewmodel,mainViewModel)
                binding.executePendingBindings()
            }
        }
    }

    companion object {
        private const val TAG = "CharacterAdapter"
    }
}
