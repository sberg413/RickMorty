package com.sberg413.rickandmorty.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()

    private val listCharacters = mutableListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_fragment)

        val characterAdapter = CharacterAdapter(this, listCharacters)
        findViewById<RecyclerView>(R.id.recycler_main).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = characterAdapter

        }

        // val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getData().observe(this,
            { t ->
                listCharacters.clear()
                t?.let { listCharacters.addAll(it.results) }
                characterAdapter.notifyDataSetChanged()
            })

    }
}