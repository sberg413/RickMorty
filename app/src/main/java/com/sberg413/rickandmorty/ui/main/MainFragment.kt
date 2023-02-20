package com.sberg413.rickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.adapters.CharacterAdapter
import com.sberg413.rickandmorty.databinding.MainFragmentBinding
import com.sberg413.rickandmorty.models.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*
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
            inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(requireContext(), layoutManager.orientation)

        characterAdapter.setCharacterClickListener { character ->
            mainViewModel.updateStateWithCharacterClicked(character)
        }

        binding?.apply {

            // characterAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            recyclerMain.adapter = characterAdapter
            recyclerMain.layoutManager = layoutManager
            recyclerMain.addItemDecoration(divider)
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "newText = $newText")
                    mainViewModel.setSearchFilter(newText)
                    return false
                }
            })

            searchBar.setOnCloseListener {
                searchBar.setQuery("", false)
                false
            }

            lifecycleScope.launchWhenStarted {
                mainViewModel.listData.collectLatest { pagingData ->
                    Log.d(TAG, "collectLatest = $pagingData")
                    characterAdapter.submitData(pagingData)
                }
            }

            lifecycleScope.launchWhenStarted {
                mainViewModel.characterClicked.collectLatest {
                    if (it != null) {
                        val action = MainFragmentDirections.actionShowDetailFragment(it)
                        findNavController().navigate(action)
                        mainViewModel.updateStateWithCharacterClicked(null)
                    }
                }
            }
        }
    }

    private val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val filterVal = parent.adapter.getItem(position) as String
            Log.d(TAG," Selected: $filterVal")
            mainViewModel.setSatusFilter(filterVal)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        (menu.findItem(R.id.spinner)?.actionView as Spinner).apply {
            onItemSelectedListener = spinnerListener
            adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.filter_options,
                        android.R.layout.simple_spinner_item
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
            setSelection(
                mainViewModel.getSelectedStatusIndex(
                    resources.getStringArray(R.array.filter_options)
                )
            )
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