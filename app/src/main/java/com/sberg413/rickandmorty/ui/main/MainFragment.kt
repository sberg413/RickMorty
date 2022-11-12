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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sberg413.rickandmorty.R
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
            inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(requireContext(), layoutManager.orientation)

        binding?.apply {

            // characterAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            recyclerMain.adapter = characterAdapter
            recyclerMain.layoutManager = layoutManager
            recyclerMain.addItemDecoration(divider)
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
//                    Log.d(TAG, "query = $query")
//                    query?.let {
//                        // mainViewModel.updateCharacterList(it)
//                        mainViewModel.setSearchFilter(it)
//                    }
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
                // mainViewModel.updateCharacterList("")
                false
            }

            // Activities can use lifecycleScope directly, but Fragments should instead use
            // viewLifecycleOwner.lifecycleScope.
            mainViewModel.listData.observe(viewLifecycleOwner) { pagingData ->
                lifecycleScope.launch {
                    Log.d(TAG, "collectLatest = $pagingData")
                    characterAdapter.submitData(pagingData)
                }
            }
        }
    }

    private val spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val filterVal = parent.adapter.getItem(position) as String
            // Toast.makeText(requireContext(), " Selected: $filterVal", Toast.LENGTH_SHORT).show()
            mainViewModel.setSatusFilter(filterVal)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val item = menu.findItem(R.id.spinner)
        val spinner = item.actionView as Spinner
        spinner.onItemSelectedListener = spinnerListener
        spinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_options,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//    }

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