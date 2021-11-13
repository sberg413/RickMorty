package com.sberg413.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.BR
import com.sberg413.rickandmorty.databinding.CharacterRowBinding
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.ui.main.MainViewModel

class CharacterAdapter(val mainViewModel: MainViewModel) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    private val listOfCharacters: MutableList<Character> = mutableListOf()

    fun replaceAllCharacters(characters: List<Character>) {
        listOfCharacters.clear()
        listOfCharacters.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val characterBinding = CharacterRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(characterBinding, mainViewModel)
    }

    override fun getItemCount(): Int = listOfCharacters.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOfCharacters[position])
    }

    // Passing in ViewDataBindng instead of CharacterRowBinding is more generic.
    class MyViewHolder(private val binding: ViewDataBinding,
                       private val mainViewModel: MainViewModel)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(character: Character) {
            binding.root.tag = character.id
            binding.setVariable(BR.character,character)
            binding.setVariable(BR.viewmodel,mainViewModel)
            binding.executePendingBindings()
        }
    }
}
