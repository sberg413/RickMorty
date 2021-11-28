package com.sberg413.rickandmorty.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.databinding.MainActivityBinding
import com.sberg413.rickandmorty.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()

    lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil
            .setContentView<MainActivityBinding>(this, R.layout.main_activity)

        characterAdapter = CharacterAdapter(mainViewModel)

        binding.recyclerMain.adapter = characterAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "query = $query")
                query?.let {
                    mainViewModel.updateCharacterList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG,"newText = $newText" )
                newText.takeIf { it =="" }?.let {
                    mainViewModel.updateCharacterList(null)
                }
                return false
            }
        })

        // Activities can use lifecycleScope directly, but Fragments should instead use
        // viewLifecycleOwner.lifecycleScope.
        mainViewModel.listData.observe(this, { pagingData ->
            lifecycleScope.launch {
                Log.d(TAG, "collectLatest = $pagingData")
                characterAdapter.submitData(pagingData)
            }
        })

        mainViewModel.isLoading.observe(this, { t ->
            Log.d(TAG, "isLoading initialized or changed. Update progress view ...")
            binding.recyclerMain.isVisible = !t
            binding.progressBar.isVisible = t
        })

        mainViewModel.navigateToDetails.observe(this, { event ->
            // Only proceed if the event has never been handled
            event.getContentIfNotHandled()?.let { character ->
                startDetailPage(this, character.id)
            }
        })

    }

    companion object {
        private const val TAG = "MainActivity"

        private fun startDetailPage(context: Context, id: Int) {
            context.startActivity(
                Intent(context , DetailActivity::class.java)
                    .putExtra("id", id.toString()))
        }
    }
}