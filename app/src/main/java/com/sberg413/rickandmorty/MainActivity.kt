package com.sberg413.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.ui.main.MainViewModel
import com.sberg413.rickandmorty.ui.main.MainViewModelFactory
import com.sberg413.rickandmorty.ui.models.Character

class MainActivity : AppCompatActivity() {

    private val listCharacters = mutableListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_fragment)

        val characterAdapter = CharacterAdapter(this, listCharacters)
        findViewById<RecyclerView>(R.id.recycler_main).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = characterAdapter

        }

        val viewModel = ViewModelProvider(this, MainViewModelFactory(this))
            .get(MainViewModel::class.java)
        viewModel.getData().observe(this,
            { t ->
                listCharacters.clear()
                t?.let { listCharacters.addAll(it.results) }
                characterAdapter.notifyDataSetChanged()
            })

    }
}