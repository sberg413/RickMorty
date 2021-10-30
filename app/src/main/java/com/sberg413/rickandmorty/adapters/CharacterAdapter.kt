package com.sberg413.rickandmorty.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.BR
import com.sberg413.rickandmorty.databinding.CharacterRowBinding
import com.sberg413.rickandmorty.models.Character

class CharacterAdapter(private val listener: CharacterListener) :
    RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    interface CharacterListener {
        fun onClickedCharacter(charIdString: String)
    }

    private val listOfCharacters: MutableList<Character> = mutableListOf()

    fun replaceAllCharacters(characters: List<Character>) {
        listOfCharacters.clear()
        listOfCharacters.addAll(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val characterBinding = CharacterRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(characterBinding, listener)
    }

    override fun getItemCount(): Int = listOfCharacters.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listOfCharacters[position])
    }

    class MyViewHolder(private val binding: ViewDataBinding, private val listener: CharacterListener)
        : RecyclerView.ViewHolder(binding.root),  View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(character: Character) {
            binding.root.tag = character.id
            binding.setVariable(BR.character,character)
            binding.executePendingBindings()
        }

        override fun onClick(v: View) {
            Log.d("CharacterAdapter", "Clicked!!!! tag = ${v.tag}")
            listener.onClickedCharacter(v.tag.toString())
        }
    }

}
