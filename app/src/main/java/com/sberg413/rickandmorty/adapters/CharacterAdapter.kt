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
import com.sberg413.rickandmorty.models.Character

import android.content.Intent
import com.sberg413.rickandmorty.ui.detail.DetailActivity


class CharacterAdapter(private val context: Context, private var list: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

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
        holder.status?.text = character.status
        holder.species?.text = character.species
        holder.itemView.tag = character.id

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
        var status: TextView? = null
        var species: TextView? = null

        init {
            image = view.findViewById(R.id.image)
            name = view.findViewById(R.id.name)
            status = view.findViewById(R.id.status)
            species = view.findViewById(R.id.species)
            view.isClickable = true
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("CharacterAdapter", "Clicked!!!! tag = ${v.tag}")
            v.context.startActivity(
                Intent(v.context, DetailActivity::class.java)
                    .putExtra("id", v.tag.toString())
            )
        }
    }

}
