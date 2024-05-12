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
import com.sberg413.rickandmorty.utils.CharacterComparator
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharacterAdapter @Inject constructor() :
    PagingDataAdapter<Character, CharacterAdapter.MyViewHolder>(CharacterComparator) {

    private var characterClickListener: ((Character)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val characterBinding = CharacterRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        Log.d(TAG, "onCreateViewHolder: $viewType")
        return MyViewHolder(characterBinding, characterClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        val character = getItem(position) ?: return
        holder.bind(character)
    }

    fun setCharacterClickListener(characterClickListener: (Character)->Unit){
        this.characterClickListener = characterClickListener
    }

    // Passing in ViewDataBindng instead of CharacterRowBinding is more generic.
    // Would be easier to support multiple types of rows.
    class MyViewHolder(private val binding: ViewDataBinding, onCharacterClicked: ((Character)->Unit)?)
        : RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener{
                (binding as CharacterRowBinding).character?.let { character ->
                    onCharacterClicked?.invoke(character)
                }
            }
        }

        fun bind(character: Character) {
            Log.d(TAG, "bind: $character")
            binding.setVariable(BR.character,character)
            // binding.executePendingBindings()
        }
    }

    companion object {
        private const val TAG = "CharacterAdapter"
    }
}
