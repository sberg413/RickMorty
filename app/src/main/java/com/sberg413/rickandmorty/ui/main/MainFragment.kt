package com.sberg413.rickandmorty.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.databinding.MainFragmentBinding
import com.sberg413.rickandmorty.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
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

        val characterAdapter = CharacterAdapter(mainViewModel)

        binding?.apply {

            recyclerMain.adapter = characterAdapter
            recyclerMain.layoutManager = LinearLayoutManager(requireContext())
            lifecycleOwner = this@MainFragment
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
                    newText.takeIf { it == "" }?.let {
                        mainViewModel.updateCharacterList(null)
                    }
                    return false
                }
            })

            // Activities can use lifecycleScope directly, but Fragments should instead use
            // viewLifecycleOwner.lifecycleScope.
            mainViewModel.listData.observe(viewLifecycleOwner, { pagingData ->
                lifecycleScope.launch {
                    Log.d(TAG, "collectLatest = $pagingData")
                    characterAdapter.submitData(pagingData)
                }
            })

//            mainViewModel.isLoading.observe(viewLifecycleOwner, { t ->
//                Log.d(TAG, "isLoading initialized or changed. Update progress view ...")
//                this.recyclerMain.isVisible = !t
//                this.progressBar.isVisible = t
//            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val TAG = "MainFragment"

        private fun startDetailPage(context: Context, id: Int) {
            context.startActivity(
                Intent(context , DetailActivity::class.java)
                    .putExtra("id", id.toString()))
        }

        fun newInstance() = MainFragment()
    }

}