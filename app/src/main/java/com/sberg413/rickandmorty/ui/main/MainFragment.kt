package com.sberg413.rickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject lateinit var characterAdapter: CharacterAdapter

    private val mainViewModel: MainViewModel by viewModels()
    private var binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(
            LayoutInflater.from(requireContext()), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {

            // characterAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            recyclerMain.adapter = characterAdapter
            recyclerMain.layoutManager = LinearLayoutManager(requireContext())
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "query = $query")
                    query?.let {
                        mainViewModel.updateCharacterList(it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "newText = $newText")
                    return false
                }
            })

            searchBar.setOnCloseListener {
                searchBar.setQuery("", false)
                mainViewModel.updateCharacterList("")
                false
            }

            // Activities can use lifecycleScope directly, but Fragments should instead use
            // viewLifecycleOwner.lifecycleScope.
            mainViewModel.listData.observe(viewLifecycleOwner, { pagingData ->
                lifecycleScope.launch {
                    Log.d(TAG, "collectLatest = $pagingData")
                    characterAdapter.submitData(pagingData)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val TAG = "MainFragment"

        @Suppress("UNUSED")
        fun newInstance() = MainFragment()
    }

}