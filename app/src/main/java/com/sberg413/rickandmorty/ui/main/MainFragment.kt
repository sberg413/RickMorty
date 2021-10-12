package com.sberg413.rickandmorty.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.models.CharacterList
import com.sberg413.rickandmorty.ui.models.Character

class MainFragment : Fragment() {

    private val listCharacters = mutableListOf<Character>()
    private lateinit var adapter: CharacterAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.main_fragment, container, false)

        val recyclerView = v.findViewById<RecyclerView>(R.id.recycler_main)
        recyclerView.layoutManager = LinearLayoutManager(v.context)

        activity?.let { it ->

            // listCharacters = mutableListOf<Character>()
            adapter = CharacterAdapter(
                it,
                listCharacters
            )
            recyclerView.adapter = adapter

            viewModel = ViewModelProvider(this, MainViewModelFactory(it)).get(MainViewModel::class.java)
            viewModel.getData().observe(it,
                Observer<CharacterList> { t ->
                    listCharacters.clear()
                    t?.let { listCharacters.addAll(it.results) }
                    adapter.notifyDataSetChanged()
                })

        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}