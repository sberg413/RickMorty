package com.sberg413.rickandmorty.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.ui.models.Character
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import androidx.core.content.ContextCompat
import com.sberg413.rickandmorty.ui.detail.DetailActivity


class CharacterAdapter(private val context: Context, private var list: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    // private val  = characterList.results

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.character_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = list[position]
        holder.name?.text = character.name
        holder.species?.text = character?.species + " | " + character?.status
        holder.itemView.tag = character

        holder.image?.let {
            Glide.with(holder.itemView)
                .load(character.image)
                .transform(CircleCrop())
                .into(it)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),  View.OnClickListener {
        var image: ImageView? = null
        var name: TextView? = null
        var species: TextView? = null

        init {
            image = view.findViewById(R.id.image)
            name = view.findViewById(R.id.name)
            species = view.findViewById(R.id.species_and_status)
            view.isClickable = true
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("CharacterAdapter", "Clicked!!!! tag = ${v.tag}")
            val intent = Intent(v.context, DetailActivity::class.java)
            v.context.startActivity(intent)
        }
    }

}
