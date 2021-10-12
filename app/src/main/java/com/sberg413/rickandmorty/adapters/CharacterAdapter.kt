package com.sberg413.rickandmorty.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.ui.models.Character

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
        holder.info1?.text = character?.name + " | " + character?.gender
        holder.info2?.text = character?.species + " | " + character?.status
        // val addressObj = character.addressObject
        // holder.address?.text = addressObj?.suite + "," + addressObj?.street + "," + addressObj?.city + "," + addressObj?.zipCode
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView? = null
        var info1: TextView? = null
        var info2: TextView? = null
        var address: TextView? = null

        init {
            name = view.findViewById(R.id.txt_user_name)
            info1 = view.findViewById(R.id.txt_user_info1)
            info2 = view.findViewById(R.id.txt_user_info2)
            address = view.findViewById(R.id.txt_user_address)
        }

    }

}
