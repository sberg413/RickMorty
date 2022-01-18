package com.sberg413.rickandmorty.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.BR
import com.sberg413.rickandmorty.databinding.CharacterRowBinding
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.ui.main.MainFragmentDirections
import com.sberg413.rickandmorty.utils.CharacterComparator
import javax.inject.Inject

class CharacterAdapter @Inject constructor() :
    PagingDataAdapter<Character, CharacterAdapter.MyViewHolder>(CharacterComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val characterBinding = CharacterRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "onCreateViewHolder: $viewType")
        return MyViewHolder(characterBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        (holder as? MyViewHolder)?.bind(getItem(position))
    }

    // Passing in ViewDataBindng instead of CharacterRowBinding is more generic.
    // Would be easier to support multiple types of rows.
    class MyViewHolder(private val binding: ViewDataBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(character: Character?) {
            Log.d(TAG, "bind: $character")
            if (character != null) {
                val clickListener = View.OnClickListener {
                    val action = MainFragmentDirections.actionShowDetailFragment(character)
                    it.findNavController().navigate(action)
                }

                binding.setVariable(BR.character,character)
                binding.setVariable(BR.clickListener, clickListener)
                binding.executePendingBindings()
            }
        }
    }

    companion object {
        private const val TAG = "CharacterAdapter"
    }
}
