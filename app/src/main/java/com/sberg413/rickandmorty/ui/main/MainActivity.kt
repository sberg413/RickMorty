package com.sberg413.rickandmorty.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()

    private val characterListener = object: CharacterAdapter.CharacterListener {
        override fun onClickedCharacter(charIdString: String) {
            startActivity(
                Intent(this@MainActivity , DetailActivity::class.java)
                    .putExtra("id", charIdString))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val characterAdapter = CharacterAdapter(characterListener)
        findViewById<RecyclerView>(R.id.recycler_main).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = characterAdapter

        }

        val searchBar = findViewById<SearchView>(R.id.search_bar)
        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "query = $query")
                query?.let {
                    mainViewModel.search(it).observe(this@MainActivity,
                        { t ->
                            characterAdapter.replaceAllCharacters(t.results)
                        })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG,"newText = $newText" )
                newText.takeIf { it =="" }?.let {
                    mainViewModel.search(it).observe(this@MainActivity,
                        { t ->
                            characterAdapter.replaceAllCharacters(t.results)
                        })
                }
                return false
            }
        })

        mainViewModel.listData.observe(this,
            { t ->
                characterAdapter.replaceAllCharacters(t.results)
            })

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}